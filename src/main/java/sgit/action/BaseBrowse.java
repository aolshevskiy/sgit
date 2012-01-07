package sgit.action;

import com.google.inject.Inject;

import sgit.dao.RepositoryDao;
import sgit.dto.SRepository;

public abstract class BaseBrowse extends Base {
	private RepositoryDao dao;
	@Inject
	void setDao(RepositoryDao dao) {
		this.dao = dao;
	}
	public RepositoryDao getDao() {return dao;}
	private String path = "";
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {return path;}
	private SRepository repository;
	public void setRepository(SRepository repository) {this.repository = repository;}	
	public SRepository getRepository() {return repository;}
	public boolean getIsSubtree() {
		return dao.isSubtree(repository, path);
	}
}
