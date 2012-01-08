package sgit.action;

import java.util.Iterator;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import org.eclipse.jgit.revwalk.RevCommit;

public class Log extends BaseBrowse {	
	public Iterator<RevCommit> getLog() {
		return getRepository().getLog(getBranch(), getPath());		
	}	
	
	private RevCommit commit;	
	public void setCommit(RevCommit commit) {
		this.commit = commit;
	}
	
	public String getAbbrev() {
		return commit.abbreviate(8).name();
	}
	
	@DefaultHandler
	public Resolution log() {
		return new ForwardResolution("/WEB-INF/sgit/log.jsp");
	}
}
