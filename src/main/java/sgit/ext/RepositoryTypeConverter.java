package sgit.ext;

import java.util.Collection;
import java.util.Locale;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import sgit.dao.RepositoryDao;
import sgit.dto.GitRepository;

import com.google.inject.Inject;

public class RepositoryTypeConverter implements TypeConverter<GitRepository> {
	private final RepositoryDao dao;
	
	@Inject
	RepositoryTypeConverter(RepositoryDao dao) {		
		this.dao = dao;
	}

	@Override
	public void setLocale(Locale locale) {}

	@Override
	public GitRepository convert(String input,
			Class<? extends GitRepository> targetType, Collection<ValidationError> errors) {
		return dao.get(input);		
	}

}
