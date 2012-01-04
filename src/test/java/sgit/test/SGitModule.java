package sgit.test;

import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SGitModule extends ServletModule {	
	@Override
	protected void configureServlets() {		
		bind(TestStripesFilter.class).in(Singleton.class);
		bind(DispatcherServlet.class).in(Singleton.class);
		Map<String,String> params = new HashMap<String, String>();
		params.put("ActionResolver.Packages", "sgit");
		params.put("ActionResolver.Class", "sgit.ActionResolver");
		filter("*.action").through(TestStripesFilter.class, params);
		serve("*.action").with(DispatcherServlet.class);
		List<File> repos = new ArrayList<File>();
		repos.add(new File("repositories/repo1"));
		repos.add(new File("repositories/repo2"));
		bind(List.class).annotatedWith(Names.named("sgit.repositories"))
			.toInstance(repos);		
	}
}
