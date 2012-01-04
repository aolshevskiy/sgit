package sgit.action;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.io.File;
import java.util.List;

public class Repository extends Base {
	private final List<File> repos;
	@Inject
	Repository(@Named("sgit.repositories") List repos) {
		this.repos = repos;
	}
	public List<File> getRepos() {return repos;}
	@DefaultHandler
	public Resolution list() {		
		return new ForwardResolution("/WEB-INF/sgit/repository/list.jsp");
	}
}
