package sgit;

import java.util.List;

import sgit.dao.RepositoryDao;
import sgit.dto.Repository;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class SGitModule extends AbstractModule {

	@Override
	protected void configure() {}
	
	@Provides
	List<Repository> provideRepositories(RepositoryDao dao) {
		return dao.getAll();
	}
}
