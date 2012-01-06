package sgit.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class Browse extends Base {
	private final static String VIEW = "/WEB-INF/sgit/browse.jsp";
	@DefaultHandler
	public Resolution index() {				
		return new ForwardResolution(VIEW);
	}
}
