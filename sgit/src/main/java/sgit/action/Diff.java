package sgit.action;

import java.io.IOException;
import java.io.InputStream;

import jygments.Lexer;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.util.HtmlUtil;

public class Diff extends BaseHighlight {
	private String highlight(String contents) {
		Lexer lexer = jygments.newLexer("diff");
		return jygments.highlight(contents, lexer, formatter);		
	}
	
	public String getDiff() throws IOException {
		InputStream diff = getRepository().getDiff(getId());
		String contents = inputStreamToString(diff);
		return highlight(contents);
	}
	
	@DefaultHandler
	public Resolution diff() {
		return new ForwardResolution("/WEB-INF/sgit/diff.jsp");
	}
}
