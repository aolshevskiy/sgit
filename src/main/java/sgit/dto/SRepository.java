package sgit.dto;

import java.io.File;

import org.eclipse.jgit.revwalk.RevCommit;

public class SRepository {
	private final File path;
	private final String name;	
	private final RevCommit lastCommit;
	
	public SRepository(File path, String name, RevCommit lastCommit) {
		this.path = path;
		this.name = name;
		this.lastCommit = lastCommit;						
	}
	
	public File getPath() {return path;}
	public String getName() {return name;}		
	public RevCommit getLastCommit() { return lastCommit;	}	
}
