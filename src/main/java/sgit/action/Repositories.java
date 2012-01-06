package sgit.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import sgit.dto.SRepository;

import com.google.inject.Inject;

public class Repositories extends Base {
	private final Collection<SRepository> repos;
	
	@Inject
	Repositories(Collection<SRepository> repos) {
		this.repos = repos;
	}
	public Collection<SRepository> getRepos() {return repos;}
	@DefaultHandler
	public Resolution list() {		
		return new ForwardResolution("/WEB-INF/sgit/repositories.jsp");
	}
}
