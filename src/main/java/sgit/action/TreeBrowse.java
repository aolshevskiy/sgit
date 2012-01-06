package sgit.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import sgit.dao.RepositoryDao;
import sgit.dto.PathEntry;
import sgit.dto.SRepository;

import com.google.inject.Inject;

abstract public class TreeBrowse extends Base {
	abstract RepositoryDao getDao();
	abstract void init();
	
	private final static String VIEW = "/WEB-INF/sgit/browse.jsp";
	
	private SRepository repository;
	private String path = "";
	private PathEntry entry;
	private List<PathEntry> entries;
	
	public void setRepository(SRepository repository) {this.repository = repository;}	
	public SRepository getRepository() {return repository;}	
	
	public void setPath(String path) {this.path = path;}
	public String getPath() {return path;}
	
	public void setEntry(PathEntry entry) {this.entry = entry;}
	
	private String getParent() {
		if(this.path.indexOf('/') != -1)
			return this.path.substring(0, this.path.lastIndexOf('/'));
		return "";
	}
	public String getAbsolutePath() {
		if(entry.equals(PathEntry.PARENT))
			return getParent();
		if(this.path.isEmpty())
			return entry.getName();
		return this.path + "/" + entry.getName();
	}
	
	public List<PathEntry> getEntries() {return entries;}
	
	private boolean isSubtree;
	public boolean getIsSubtree() {return isSubtree;}
	
	String basename;
	public String getBasename() {return basename;}
		
	@DefaultHandler
	public Resolution index() {
		isSubtree = getDao().isSubtree(repository, path);
		if(!isSubtree) {
			basename = this.path.substring(this.path.lastIndexOf('/') + 1, this.path.length());
			this.path = getParent();			
		}
		entries = getDao().getEntries(repository, path);
		init();
		return new ForwardResolution(VIEW);
	}
}
