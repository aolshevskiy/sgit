package sgit.action;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.io.File;
import java.util.List;

import sgit.dao.RepositoryDao;
import sgit.dto.Repository;

public class Repositories extends Base {
	private final List<Repository> repos;
	
	@Inject
	Repositories(List<Repository> repos) {
		this.repos = repos;
	}
	public List<Repository> getRepos() {return repos;}
	@DefaultHandler
	public Resolution list() {		
		return new ForwardResolution("/WEB-INF/sgit/repositories.jsp");
	}
}
