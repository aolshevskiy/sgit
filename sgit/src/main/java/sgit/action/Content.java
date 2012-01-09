package sgit.action;

import java.io.IOException;
import java.io.InputStream;

import jygments.Lexer;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.util.HtmlUtil;

public class Content extends BaseHighlight {	
	private String highlight(String filename, String code) {		
		Lexer lexer = jygments.newLexerForFilename(filename);
		return jygments.highlight(code, lexer);		
	}
	
	private static String basename(String path) {
		return path.substring(path.lastIndexOf('/')+1, path.length());
	}
	
	public String getContent() throws IOException {
		InputStream is = getRepository().getFile(getBranch(), getPath());		
		String contents = inputStreamToString(is);
		try {
			return highlight(basename(getPath()), contents);
		} catch(IllegalArgumentException e) {			
			return "<pre>"+HtmlUtil.encode(contents)+"</pre>";
		}
	}	
	
	@DefaultHandler
	public Resolution content() {
		return new ForwardResolution("/WEB-INF/sgit/content.jsp");
	}
}
