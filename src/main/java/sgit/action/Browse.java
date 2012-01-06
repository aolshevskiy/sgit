package sgit.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.errors.AmbiguousObjectException;

import sgit.dto.PathInfo;
import sgit.dto.Repository;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class Browse extends Base {
	private final static String VIEW = "/WEB-INF/sgit/browse.jsp";
	
	private Repository repository;
	public void setRepository(Repository repository) {
		this.repository = repository;
	}	
	public Repository getRepository() {return repository;}
	
	private String path = "";
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {return path;}	 
	public String getParentPath() throws IOException {
		return new File(path + "/..").getCanonicalPath();
	}
	
	private List<PathInfo> paths;
	public List<PathInfo> getPaths() {return paths;}
	
		
	@DefaultHandler
	public Resolution index() {
		paths = repository.getPaths(path);
		return new ForwardResolution(VIEW);
	}
}
