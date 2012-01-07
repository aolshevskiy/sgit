package sgit.dao;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;

import sgit.dto.GitRepository;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class RepositoryDao {
	@Inject
	RepositoryDao(@Named("sgit.repositories") List<File> repositories) throws NoHeadException, JGitInternalException, IOException {
		this.repositories = new HashMap<String, GitRepository>();				
		for(File path: repositories) {
			GitRepository repository = new GitRepository(path); 
			this.repositories.put(repository.getName(), repository);
		}
	}
	private final Map<String, GitRepository> repositories;
	
	public GitRepository get(String name) {
		return repositories.get(name);
	}
	
	public Collection<GitRepository> getAll() {
		return repositories.values();
	}	
}
