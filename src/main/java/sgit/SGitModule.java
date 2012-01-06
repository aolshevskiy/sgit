package sgit;

import java.util.Collection;

import sgit.dao.RepositoryDao;
import sgit.dto.SRepository;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class SGitModule extends AbstractModule {

	@Override
	protected void configure() {}
	
	@Provides
	Collection<SRepository> provideRepositories(RepositoryDao dao) {
		return dao.getAll();
	}
}
