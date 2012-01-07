package sgit.action;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

import sgit.dao.RepositoryDao;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class Log extends BaseBrowse {	
	public Iterator<RevCommit> getLog() {
		return getRepository().getLog(getPath());		
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
