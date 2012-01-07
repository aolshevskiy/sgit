package sgit.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;

import sgit.dao.RepositoryDao;
import sgit.dto.Breadcrumb;
import sgit.dto.SRepository;

public abstract class BaseBrowse extends Base {
	private RepositoryDao dao;
	@Inject
	void setDao(RepositoryDao dao) {
		this.dao = dao;
	}
	public RepositoryDao getDao() {return dao;}
	private String path = "";
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {return path;}
	private SRepository repository;
	public void setRepository(SRepository repository) {this.repository = repository;}	
	public SRepository getRepository() {return repository;}
	public boolean getIsSubtree() {
		return dao.isSubtree(repository, path);
	}	
	private static String join(List<String> chunks) {
		StringBuilder b = new StringBuilder();
		int len = chunks.size();
		for(int i = 0; i < len; i++) {
			if(i!=0)
				b.append("/");
			b.append(chunks.get(i));			
		}
		return b.toString();
	}
	public List<Breadcrumb> getBreadcrumbs() {
		if(path.isEmpty())
			return Collections.emptyList();
		List<Breadcrumb> result = new ArrayList<Breadcrumb>();
		List<String> acc = new ArrayList<String>();
		String chunks[] = path.split("/");
		int len = chunks.length;
		for(int i = 0; i < len; i++) {
			acc.add(chunks[i]);
			String path = (i==len-1)?null:join(acc); 
			result.add(new Breadcrumb(chunks[i], path));
		}		
		return result;
	}
}
