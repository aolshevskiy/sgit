package sgit;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.NameBasedActionResolver;

import com.google.inject.Injector;

public class ActionResolver extends NameBasedActionResolver {
	private Injector injector;
	@Override
	public void init(Configuration configuration) throws Exception {
		injector = (Injector) configuration.getServletContext().getAttribute(Injector.class.getName());
		super.init(configuration);
	}

	@Override
	protected ActionBean makeNewActionBean(Class<? extends ActionBean> type, ActionBeanContext context) throws Exception {
		return injector.getInstance(type);
	}
}
