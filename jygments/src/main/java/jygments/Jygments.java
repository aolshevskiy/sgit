package jygments;

import org.python.core.PyFunction;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

final public class Jygments {
	private final PythonInterpreter i = new PythonInterpreter();
	{
		i.exec("import sys");
		String jygmentsJar = Jygments.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		i.exec("sys.path.append('"+jygmentsJar+"/Lib')");		
		i.exec("from pygments import highlight");
		i.exec("from pygments.lexers import get_lexer_by_name, get_lexer_for_filename, guess_lexer");
		i.exec("from pygments.formatters import HtmlFormatter");
	}
	public Lexer newLexer(String name) {
		return new Lexer(i.get("get_lexer_by_name").__call__(new PyString(name)));
	}
	public Lexer newLexerForFilename(String fileName) {
		try {
			return new Lexer(i.get("get_lexer_for_filename").__call__(new PyString(fileName)));
		} catch(RuntimeException e) {
			throw new IllegalArgumentException(e);
		}
	}
	public Lexer newLexerForCode(String code) {
		try {
			return new Lexer(i.get("guess_lexer").__call__(new PyString(code)));
		} catch(RuntimeException e) {			
			throw new IllegalArgumentException(e);
		}
	}
	public HtmlFormatter newHtmlFormatter(String params) {
		return new HtmlFormatter(i.eval("HtmlFormatter(" + params + ")"));
	}
	
	public String highlight(String code, Lexer lexer, Formatter formatter) {
		PyFunction f = i.get("highlight", PyFunction.class);
		return f.__call__(new PyString(code), lexer.getLexer(), formatter.getFormatter()).asString();
	}
	public String highlight(String code, Lexer lexer) {
		PyFunction f = i.get("highlight", PyFunction.class);		
		return f.__call__(new PyString(code), lexer.getLexer(), newHtmlFormatter("").getFormatter()).asString();
	}
	
}
