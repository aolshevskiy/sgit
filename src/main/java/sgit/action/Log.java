package sgit.action;

import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

import sgit.dao.RepositoryDao;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class Log extends BaseBrowse {
	@Inject
	Log(RepositoryDao dao) {
		this.dao = dao;
	}	
	
	public Iterable<RevCommit> getLog() {
		return dao.getLog(getRepository(), getPath());
	}
	
	public Resolution content() {
		return null;
	}
}
