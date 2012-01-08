package sgit.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class SGitModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new sgit.SGitModule());		
	}
	
	@Provides @Named("sgit.repositories")
	List<File> provideRepositories() {
		List<File> result = new ArrayList<File>();		
		result.add(new File(SGitModule.class.getResource("/xsbt-web-plugin.git").getPath()));
		result.add(new File(SGitModule.class.getResource("/sgit.git").getPath()));
		return result;
	}
}
