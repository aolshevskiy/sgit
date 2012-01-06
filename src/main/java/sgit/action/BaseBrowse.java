package sgit.action;

import sgit.dao.RepositoryDao;
import sgit.dto.SRepository;

public abstract class BaseBrowse extends Base {
	protected RepositoryDao dao;
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
