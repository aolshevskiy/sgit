package sgit.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.util.HtmlUtil;

public class Diff extends BaseBrowse {	
	public String getDiff() throws IOException {
		InputStream diff = getRepository().getDiff(getId());
		BufferedReader reader = new BufferedReader(new InputStreamReader(diff));
		StringBuilder result = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
			result.append(line);
			result.append("\n");
		}
		return  HtmlUtil.encode(result.toString());
	}
	
	@DefaultHandler
	public Resolution diff() {
		return new ForwardResolution("/WEB-INF/sgit/diff.jsp");
	}
}
