package sgit.util;

final public class GitUtils {
	public static String stripGitSuffix(String name) {
		if(name.endsWith(".git"))
			return name.substring(0, name.lastIndexOf('.'));
		return name;
	}
}
