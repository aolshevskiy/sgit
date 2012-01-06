package sgit.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgit.dto.Repository;
import sgit.util.GitUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class RepositoryDao {
	private final Map<String, File> repositories;
	
	@Inject
	RepositoryDao(@Named("sgit.repositories") List<File> repositories) {
		this.repositories = new HashMap<String, File>();
		List<File> rs = repositories;
		for(File repo: rs)
			this.repositories.put(GitUtils.stripGitSuffix(repo.getName()), repo);
	}
	
	public Repository get(String name) {
		return new Repository(repositories.get(name));
	}
	
	public List<Repository> getAll() {
		List<Repository> result = new ArrayList<Repository>();
		for(String name: repositories.keySet())
			result.add(get(name));
		return result;
	}
}
