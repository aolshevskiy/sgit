package sgit.dto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.TreeEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import sgit.util.GitUtils;

public class Repository {
	private final String name;
	private final org.eclipse.jgit.lib.Repository repo;
	private final Git git;
	
	public Repository(File path) {				
		this.name = GitUtils.stripGitSuffix(path.getName());
		FileRepositoryBuilder builder = new FileRepositoryBuilder();		
		try {
			repo = builder.setGitDir(path)
					.readEnvironment()
					.findGitDir()
					.build();			
		} catch (IOException e) {			
			throw new RuntimeException(e);
		}		
		git = new Git(repo);
	}
	
	public String getName() {return name;}
	
	public RevCommit getLastCommit() throws NoHeadException, JGitInternalException {
		return git.log().call().iterator().next();			
	}	
	
	public List<PathInfo> getPaths(String path) {
		if(!path.equals(""))
			path = path.substring(1);		
		try {
			ObjectId head = repo.resolve(Constants.HEAD);
			RevWalk rw = new RevWalk(repo);			
			RevTree tree = rw.parseCommit(head).getTree();
			TreeWalk tw = new TreeWalk(repo);			
			tw.addTree(tree);
			List<PathInfo> result = new ArrayList<PathInfo>();
			int len = 0;
			if(!path.equals("")) {
				len = path.split("/").length;
				result.add(PathInfo.PARENT);				
				tw.setFilter(PathFilter.create(path));				
			}					
			while(tw.next()) {
				if(tw.isSubtree() && tw.getDepth() < len) {
					tw.enterSubtree();					
					continue;
				}				
				result.add(new PathInfo(
						tw.getNameString(), tw.isSubtree()));
			}
			Collections.sort(result, new Comparator<PathInfo>() {
				@Override
				public int compare(PathInfo o1, PathInfo o2) {
					int result;
					if((result = -o1.getIsDirectory().compareTo(o2.getIsDirectory()))==0)
						return o1.getPath().compareTo(o2.getPath());
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
}
