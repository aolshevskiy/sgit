package sgit.action;

import com.google.inject.Inject;

import sgit.dao.RepositoryDao;

public class Browse extends ContentBrowse {
	private final RepositoryDao dao;
	
	@Inject
	Browse(RepositoryDao dao) {
		this.dao = dao;
	}

	@Override
	RepositoryDao getDao() {
		return this.dao;
	}

	@Override
	void init() {		
		
	}
}
