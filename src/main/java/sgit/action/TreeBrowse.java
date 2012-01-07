package sgit.action;

import java.util.List;

import net.sourceforge.stripes.action.Resolution;
import sgit.dao.RepositoryDao;
import sgit.dto.PathEntry;
import sgit.dto.SRepository;
import sgit.util.Path;

import com.google.inject.Inject;

public class TreeBrowse extends BaseBrowse {	
	private PathEntry entry;
	private List<PathEntry> entries;	
	
	public void setEntry(PathEntry entry) {this.entry = entry;}
	
	public String getAbsolutePath() {
		if(entry == PathEntry.PARENT)
			return Path.getParent(getPath());
		if(getPath().isEmpty())
			return entry.getName();
		return getPath() + "/" + entry.getName();
	}
	
	public List<PathEntry> getEntries() {return entries;}	
	
	public Resolution init() {
		if(!getIsSubtree())	
			setPath(Path.getParent(getPath()));		
		entries = getDao().getEntries(getRepository(), getPath());
		return null;
	}
}
