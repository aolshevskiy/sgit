package sgit.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.inject.Inject;

import jygments.HtmlFormatter;
import jygments.Jygments;

public abstract class BaseHighlight extends BaseBrowse {
	@Inject
	protected Jygments jygments;	

	protected static String inputStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder result = new StringBuilder();
		String line;		
		while((line = reader.readLine()) != null) {
			result.append(line);
			result.append("\n");
		}
		return result.toString();
	}	
}