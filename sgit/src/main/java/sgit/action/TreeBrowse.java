package sgit.action;

import java.util.List;

import net.sourceforge.stripes.action.Resolution;
import sgit.dto.PathEntry;
import sgit.util.Path;

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
		if(!getRepository().isSubtree(getBranch(), getPath()))	
			setPath(Path.getParent(getPath()));		
		entries = getRepository().getEntries(getBranch(), getPath());
		return null;
	}
}
