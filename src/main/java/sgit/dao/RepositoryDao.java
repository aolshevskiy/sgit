package sgit.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import sgit.dto.PathEntry;
import sgit.dto.SRepository;
import sgit.util.GitUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class RepositoryDao {	
	private final Map<String, SRepository> repositories;	
	
	private static Repository buildRepository(File path) throws IOException {		
		FileRepositoryBuilder builder = new FileRepositoryBuilder();		
		return builder.setGitDir(path)
				.readEnvironment()
				.findGitDir()
				.build();		
	}
	private static Repository buildRepository(SRepository repo) throws IOException {		
		return buildRepository(repo.getPath());		
	}
	
	private static SRepository newRepository(File path) throws NoHeadException, JGitInternalException, IOException {
		String name = GitUtils.stripGitSuffix(path.getName());
		Repository grepo = buildRepository(path);
		Git git = new Git(grepo);				
		RevCommit commit = git.log().call().iterator().next();				
		return new SRepository(path, name, commit);		
	}
	
	@Inject
	RepositoryDao(@Named("sgit.repositories") List<File> repositories) throws NoHeadException, JGitInternalException, IOException {
		this.repositories = new HashMap<String, SRepository>();				
		for(File path: repositories) {
			SRepository repository = newRepository(path); 
			this.repositories.put(repository.getName(), repository);
		}
	}
	
	public SRepository get(String name) {
		return repositories.get(name);
	}
	
	public Collection<SRepository> getAll() {
		return repositories.values();
	}	
	
	private static RevTree getHeadTree(Repository repo) throws AmbiguousObjectException, IOException {
		ObjectId head = repo.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(repo);			
		return rw.parseCommit(head).getTree();
	}
	
	private static TreeWalk getPathTreeWalk(SRepository srepo, String path) throws IOException {
		Repository repo = buildRepository(srepo);
		return getPathTreeWalk(repo, path);
	}
	
	private static TreeWalk getPathTreeWalk(Repository repo, String path) throws AmbiguousObjectException, IOException {		
		RevTree tree = getHeadTree(repo);		
		return TreeWalk.forPath(repo, path, tree);
	}
	
	public List<PathEntry> getEntries(SRepository srepo, String path) {						
		try {
			Repository repo = buildRepository(srepo);
			RevTree tree = getHeadTree(repo);
			TreeWalk tw = new TreeWalk(repo);			
			tw.addTree(tree);
			List<PathEntry> result = new ArrayList<PathEntry>();
			int len = 0;
			if(!path.isEmpty()) {
				len = path.split("/").length;
				result.add(PathEntry.PARENT);				
				tw.setFilter(PathFilter.create(path));				
			}					
			while(tw.next()) {
				if(tw.isSubtree() && tw.getDepth() < len) {
					tw.enterSubtree();					
					continue;
				}				
				result.add(new PathEntry(
						tw.getNameString(), tw.isSubtree()));
			}
			Collections.sort(result);
			return result;
		} catch (AmbiguousObjectException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);			
		}
	}

	public boolean isSubtree(SRepository repository, String path) {
		if(path.isEmpty())
			return true;
		try {
			Repository repo = buildRepository(repository);
			return getPathTreeWalk(repository, path).isSubtree();
		} catch (MissingObjectException e) {
			throw new RuntimeException(e);
		} catch (IncorrectObjectTypeException e) {
			throw new RuntimeException(e);
		} catch (CorruptObjectException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Iterator<RevCommit> getLog(SRepository repository, String path) {		
		try {
			Repository repo = buildRepository(repository);			
			LogCommand log = new Git(repo).log();
			if(!path.isEmpty())
				log.addPath(path);
			return log.call().iterator();
		} catch (NoHeadException e) {
			throw new RuntimeException(e);		
		} catch (JGitInternalException e) {			
			throw new RuntimeException(e);
		} catch (IOException e) {			
			throw new RuntimeException(e);
		}		
	}
	
	public InputStream getFile(SRepository repository, String path) {
		try {
			Repository repo = buildRepository(repository.getPath());
			TreeWalk tw = getPathTreeWalk(repository, path);
			ObjectId objectId = tw.getObjectId(0);
			ObjectLoader loader = repo.open(objectId);
			return loader.openStream();
		} catch (IOException e) {			
			throw new RuntimeException(e);
		}
	}	
}
