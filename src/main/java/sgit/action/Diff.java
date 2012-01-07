package sgit.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class Diff extends BaseBrowse {
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDiff() {
		return null;
	}
	
	@DefaultHandler
	public Resolution diff() {
		return new ForwardResolution("/WEB-INF/sgit/diff.jsp");
	}
}
