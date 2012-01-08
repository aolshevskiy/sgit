package sgit.util;

public final class Path {
	public static String getParent(String path) {
		if(path.indexOf('/') != -1)
			return path.substring(0, path.lastIndexOf('/'));
		return "";
	}
}
