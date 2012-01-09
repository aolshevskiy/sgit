package sgit.standalone;

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
		for(String repo: System.getProperty("repositories").split(File.pathSeparator))
			result.add(new File(repo));		
		return result;
	}
}
