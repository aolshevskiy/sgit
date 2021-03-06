package sgit.standalone;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;

public class ServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {		
		return Guice.createInjector(Stage.PRODUCTION, new SGitModule());
	}

}
