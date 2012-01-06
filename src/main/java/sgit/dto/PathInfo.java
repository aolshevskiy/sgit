package sgit.dto;

public class PathInfo {
	public static final PathInfo PARENT = new PathInfo("..", true);
	private final String path;
	private final Boolean isDirectory;
	PathInfo(String path, boolean isDirectory) {
		this.path = path;
		this.isDirectory = isDirectory;
	}
	public String getPath() {return path;}
	public Boolean getIsDirectory() {return isDirectory;}
	public Boolean getIsParent() { return equals(PARENT); }
}

