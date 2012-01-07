package sgit.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Named;
import com.google.inject.servlet.ServletModule;

public class SGitModule extends ServletModule {	
	@Override
	protected void configureServlets() {		
		bind(StripesFilter.class).in(Singleton.class);
		bindInterceptor(
				Matchers.subclassesOf(StripesFilter.class), 
				new InitMatcher(), 
				new MarkerUpdater());
		bind(DispatcherServlet.class).in(Singleton.class);
		Map<String,String> params = new HashMap<String, String>();
		params.put("ActionResolver.Packages", "sgit");
		params.put("ActionResolver.Class", "sgit.ActionResolver");
		params.put("TypeConverterFactory.Class", "sgit.TypeConverterFactory");
		params.put("Extension.Packages", "sgit.ext");
		filter("*.action").through(StripesFilter.class, params);
		serve("*.action").with(DispatcherServlet.class);		
		install(new sgit.SGitModule());
	}
	
	@Provides @Named("sgit.repositories")
	List<File> provideRepositories() {
		List<File> repos = new ArrayList<File>();		
		repos.add(new File("repositories/xsbt-web-plugin.git"));
		return repos;
	}
}
