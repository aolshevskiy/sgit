package sgit.test;

import net.sourceforge.stripes.controller.StripesFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;

public class TestStripesFilter extends StripesFilter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		File f = new File("app.init");
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		f.setLastModified(System.currentTimeMillis());
	}
}
