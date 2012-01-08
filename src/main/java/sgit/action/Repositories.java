package sgit.action;

import java.util.Collection;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import sgit.dto.GitRepository;

import com.google.inject.Inject;

@UrlBinding("/")
public class Repositories extends Base {
	private final Collection<GitRepository> repos;	
	
	@Inject
	Repositories(Collection<GitRepository> repos) {
		this.repos = repos;		
	}
	public Collection<GitRepository> getRepos() {return repos;}		
	
	@DefaultHandler
	public Resolution list() {		
		return new ForwardResolution("/WEB-INF/sgit/repositories.jsp");
	}
}
