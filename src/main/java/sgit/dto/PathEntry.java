package sgit.dto;

public class PathEntry {
	public static final PathEntry PARENT = new PathEntry("..", true);
	private final String name;	
	private final Boolean isDirectory;
	public PathEntry(String name, boolean isDirectory) {
		this.name = name;		
		this.isDirectory = isDirectory;
	}
	public String getName() {return name;}
	public Boolean getIsDirectory() {return isDirectory;}
	public Boolean getIsParent() { return equals(PARENT); }
}

