package sgit.action;

import java.util.Collection;

import org.eclipse.jgit.revwalk.RevCommit;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import sgit.dao.RepositoryDao;
import sgit.dto.GitRepository;

import com.google.inject.Inject;

public class Repositories extends Base {
	private final Collection<GitRepository> repos;
	private final RepositoryDao dao;
	
	@Inject
	Repositories(Collection<GitRepository> repos, RepositoryDao dao) {
		this.repos = repos;
		this.dao = dao;
	}
	public Collection<GitRepository> getRepos() {return repos;}
	
	private GitRepository repository; 
	public void setRepository(GitRepository repository) {
		this.repository = repository;
	}	
	
	@DefaultHandler
	public Resolution list() {		
		return new ForwardResolution("/WEB-INF/sgit/repositories.jsp");
	}
}
