package sgit.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Formatter;

import javax.swing.filechooser.FileNameExtensionFilter;

import sun.tools.java.ClassNotFound;

import jygments.HtmlFormatter;
import jygments.Jygments;
import jygments.Lexer;

import com.google.inject.Inject;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.util.HtmlUtil;

public class Content extends BaseBrowse {
	private final Jygments jygments;
	private final HtmlFormatter formatter;
	
	@Inject
	Content(Jygments jygments) {
		this.jygments = jygments;
		this.formatter = jygments.newHtmlFormatter("");
	}
	
	private String highlight(String filename, String code) {
		Lexer lexer = jygments.newLexerForFilename(filename);		
		return jygments.highlight(code, lexer, formatter);
	}
	
	private static String inputStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder result = new StringBuilder();
		String line;		
		while((line = reader.readLine()) != null) {
			result.append(line);
			result.append("\n");
		}
		return result.toString();
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
	
	public String getStyleDefs() {
		return formatter.getStyleDefs(".highlight");
	}
	
	@DefaultHandler
	public Resolution content() {
		return new ForwardResolution("/WEB-INF/sgit/content.jsp");
	}
}
