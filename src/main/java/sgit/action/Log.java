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
	@Inject
	Log(RepositoryDao dao) {
		this.dao = dao;
	}	
	
	public Iterator<RevCommit> getLog() {
		return dao.getLog(getRepository(), getPath());		
	}
	
	private RevCommit commit;	
	public void setCommit(RevCommit commit) {
		System.out.println(commit.getName());
		this.commit = commit;
	}
	
	public String getAbbreviation() {
		return commit.getName().substring(0, 8);
	}
	
	@DefaultHandler
	public Resolution log() {
		return new ForwardResolution("/WEB-INF/sgit/log.jsp");
	}
}
