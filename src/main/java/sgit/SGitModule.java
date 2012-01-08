package sgit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import sgit.dao.RepositoryDao;
import sgit.dto.GitRepository;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class SGitModule extends ServletModule {

	@Override
	protected void configureServlets() {
		bind(StripesFilter.class).in(Singleton.class);
		bind(DispatcherServlet.class).in(Singleton.class);
		Map<String,String> params = new HashMap<String, String>();
		params.put("ActionResolver.Packages", "sgit");
		params.put("ActionResolver.Class", "sgit.ActionResolver");
		params.put("TypeConverterFactory.Class", "sgit.TypeConverterFactory");
		params.put("Extension.Packages", "sgit.ext");
		filter("*.action", "/").through(StripesFilter.class, params);
		serve("*.action", "/").with(DispatcherServlet.class);
		bind(RepositoryDao.class).in(Singleton.class);
	}
	
	@Provides
	Collection<GitRepository> provideRepositories(RepositoryDao dao) {
		return dao.getAll();
	}
}
