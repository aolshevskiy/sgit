package sgit.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
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
	
	private RevTree getCommitTree(ObjectId id) throws AmbiguousObjectException, IOException {		
		RevWalk rw = new RevWalk(repository);			
		return rw.parseCommit(id).getTree();
	}	
	
	private ObjectId resolveBranch(String name) throws AmbiguousObjectException, IOException {
		return repository.resolve("refs/heads/" + name);
	}
	
	private TreeWalk getPathTreeWalk(String branch, String path) throws AmbiguousObjectException, IOException {		
		RevTree tree = getCommitTree(resolveBranch(branch));		
		return TreeWalk.forPath(repository, path, tree);
	}	
	
	public List<PathEntry> getEntries(String branch, String path) {						
		try {			
			RevTree tree = getCommitTree(resolveBranch(branch));
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
	
	public boolean isSubtree(String branch, String path) {
		if(path.isEmpty())
			return true;
		try {			
			return getPathTreeWalk(branch, path).isSubtree();
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
	
	public Iterator<RevCommit> getLog(String branch, String path) {		
		try {					
			LogCommand log = git.log();
			log.add(resolveBranch(branch));
			if(!path.isEmpty())
				log.addPath(path);
			return log.call().iterator();
		} catch (NoHeadException e) {
			throw new RuntimeException(e);		
		} catch (JGitInternalException e) {			
			throw new RuntimeException(e);
		} catch (MissingObjectException e) {
			throw new RuntimeException(e);
		} catch (IncorrectObjectTypeException e) {
			throw new RuntimeException(e);
		} catch (AmbiguousObjectException e) {			
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public InputStream getFile(String branch, String path) {
		try {			
			TreeWalk tw = getPathTreeWalk(branch, path);
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
	
	public InputStream getDiff(String id) {
		try {			
			ObjectId objectId = repository.resolve(id);			
			RevWalk revWalk = new RevWalk(repository);
			RevCommit commit = revWalk.parseCommit(objectId);
			RevCommit parent = revWalk.parseCommit(commit.getParent(0));
			AbstractTreeIterator treeIterator = new CanonicalTreeParser(null, repository.newObjectReader(), commit.getTree());
			AbstractTreeIterator parentTreeIterator = new CanonicalTreeParser(null, repository.newObjectReader(), parent.getTree());
			List<DiffEntry> entries = git.diff()
				.setNewTree(treeIterator)
				.setOldTree(parentTreeIterator)
				.call();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DiffFormatter fmt = new DiffFormatter(out);
			fmt.setRepository(repository);							
			fmt.format(entries);		
			return new ByteArrayInputStream(out.toByteArray());
		} catch (AmbiguousObjectException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}	
	}
	
	private static String branchName(String branch) {
		return branch.substring(branch.lastIndexOf('/') + 1, branch.length());
	}
	
	public String getDefaultBranch() {
		List<Ref> branches = git.branchList().call();
		for(Ref r: branches) {	
			String name = branchName(r.getName());
			if(name.equals("master"))
				return name;
		}
		return branchName(branches.get(0).getName());
	}
	
	public List<String> getBranchList(String exclude) {
		List<Ref> branches = git.branchList().call();
		List<String> result = new ArrayList<String>();
		for(Ref r: branches) {	
			String name = branchName(r.getName());
			if(!name.equals(exclude))
				result.add(name);
		}
		return result;
	}
}
