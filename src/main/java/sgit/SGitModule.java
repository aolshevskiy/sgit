package sgit;

import java.util.Collection;

import sgit.dao.RepositoryDao;
import sgit.dto.GitRepository;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class SGitModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RepositoryDao.class).in(Singleton.class);
	}
	
	@Provides
	Collection<GitRepository> provideRepositories(RepositoryDao dao) {
		return dao.getAll();
	}
}
