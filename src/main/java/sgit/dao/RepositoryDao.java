package sgit.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
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
	
	private static Repository buildRepository(File path) {
		if(!path.exists())
			throw new IllegalArgumentException(new FileNotFoundException(path + " not found."));
		FileRepositoryBuilder builder = new FileRepositoryBuilder();		
		try {
			return builder.setGitDir(path)
					.readEnvironment()
					.findGitDir()
					.build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private SRepository newRepository(File path) {
		String name = GitUtils.stripGitSuffix(path.getName());
		Repository grepo = buildRepository(path);
		Git git = new Git(grepo);
		RevCommit commit = null;
		try {
			commit = git.log().call().iterator().next();
		} catch (NoHeadException e) {			
		} catch (JGitInternalException e) { 
			throw new RuntimeException(e);
		}		
		return new SRepository(path, name, commit);		
	}
	
	@Inject
	RepositoryDao(@Named("sgit.repositories") List<File> repositories) {
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
	
	private RevTree getHeadTree(Repository repo) throws AmbiguousObjectException, IOException {
		ObjectId head = repo.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(repo);			
		return rw.parseCommit(head).getTree();
	}
	
	public List<PathEntry> getEntries(SRepository srepo, String path) {
		Repository repo = buildRepository(srepo.getPath());				
		try {
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
			Collections.sort(result, new Comparator<PathEntry>() {
				@Override
				public int compare(PathEntry o1, PathEntry o2) {
					int result;
					if((result = -o1.getIsDirectory().compareTo(o2.getIsDirectory()))==0)
						return o1.getName().compareTo(o2.getName());
					return result;
				}			
			});
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
			Repository repo = buildRepository(repository.getPath());
			RevTree tree = getHeadTree(repo);		
			return TreeWalk.forPath(repo, path, tree).isSubtree();
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
	
}
