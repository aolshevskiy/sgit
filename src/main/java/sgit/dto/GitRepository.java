package sgit.dto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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

import sgit.util.GitUtils;

public class GitRepository {	
	private final String name;	
	private final Repository repository;
	private final Git git;
	
	private static Repository buildRepository(File path) throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		return builder.setGitDir(path)
				.readEnvironment()
				.findGitDir()
				.build();
	}
	
	public GitRepository(File path) {		
		name = GitUtils.stripGitSuffix(path.getName());
		try {
			repository = buildRepository(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		git = new Git(repository);
	}
	
	public String getName() {return name;}
	
	private RevTree getHeadTree() throws AmbiguousObjectException, IOException {
		ObjectId head = repository.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(repository);			
		return rw.parseCommit(head).getTree();
	}	
	
	private TreeWalk getPathTreeWalk(String path) throws AmbiguousObjectException, IOException {		
		RevTree tree = getHeadTree();		
		return TreeWalk.forPath(repository, path, tree);
	}	
	
	public List<PathEntry> getEntries(String path) {						
		try {			
			RevTree tree = getHeadTree();
			TreeWalk tw = new TreeWalk(repository);			
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
	
	public boolean isSubtree(String path) {
		if(path.isEmpty())
			return true;
		try {			
			return getPathTreeWalk(path).isSubtree();
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
	
	public Iterator<RevCommit> getLog(String path) {		
		try {					
			LogCommand log = git.log();
			if(!path.isEmpty())
				log.addPath(path);
			return log.call().iterator();
		} catch (NoHeadException e) {
			throw new RuntimeException(e);		
		} catch (JGitInternalException e) {			
			throw new RuntimeException(e);
		}		
	}
	
	public InputStream getFile(String path) {
		try {			
			TreeWalk tw = getPathTreeWalk(path);
			ObjectId objectId = tw.getObjectId(0);
			ObjectLoader loader = repository.open(objectId);
			return loader.openStream();
		} catch (IOException e) {			
			throw new RuntimeException(e);
		}
	}
	
	public RevCommit getLastCommit() {
		try {			
			return git.log().call().iterator().next();
		} catch (NoHeadException e) {
			throw new RuntimeException(e);
		} catch (JGitInternalException e) {			
			throw new RuntimeException(e);
		}
	}
}
