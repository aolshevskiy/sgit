package sgit.action;

import java.util.List;

import net.sourceforge.stripes.action.Resolution;
import sgit.dao.RepositoryDao;
import sgit.dto.PathEntry;
import sgit.dto.SRepository;

import com.google.inject.Inject;

public class TreeBrowse extends Base {
	private final RepositoryDao dao;
	@Inject
	TreeBrowse(RepositoryDao dao) {
		this.dao = dao;
	}	
	
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
		if(entry == PathEntry.PARENT)
			return getParent();
		if(this.path.isEmpty())
			return entry.getName();
		return this.path + "/" + entry.getName();
	}
	
	public List<PathEntry> getEntries() {return entries;}
	
	public Resolution init() {
		entries = dao.getEntries(repository, path);
		return null;
	}
}
