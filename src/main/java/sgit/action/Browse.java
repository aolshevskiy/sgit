package sgit.action;

import com.google.inject.Inject;

import sgit.dao.RepositoryDao;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class Browse extends BaseBrowse {	
	@Inject
	Browse(RepositoryDao dao) {
		this.dao = dao;
	}	
	private final static String VIEW = "/WEB-INF/sgit/browse.jsp";		
	
	@DefaultHandler
	public Resolution index() {				
		return new ForwardResolution(VIEW);
	}
}
