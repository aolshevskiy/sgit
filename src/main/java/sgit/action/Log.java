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
		return getDao().getLog(getRepository(), getPath());		
	}	
	
	@DefaultHandler
	public Resolution log() {
		return new ForwardResolution("/WEB-INF/sgit/log.jsp");
	}
}
