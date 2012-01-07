package sgit.dto;

public class Breadcrumb {
	private final String label;
	private final String path;
	public Breadcrumb(String label, String path) {
		this.label = label;
		this.path = path;
	}
	public String getLabel() {return label;}
	public String getPath() {return path;}
}
