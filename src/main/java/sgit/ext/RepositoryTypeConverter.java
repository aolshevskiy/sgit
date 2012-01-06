package sgit.ext;

import java.util.Collection;
import java.util.Locale;

import com.google.inject.Inject;

import sgit.dao.RepositoryDao;
import sgit.dto.Repository;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class RepositoryTypeConverter implements TypeConverter<Repository> {
	private final RepositoryDao dao;
	
	@Inject
	RepositoryTypeConverter(RepositoryDao dao) {
		this.dao = dao;
	}

	@Override
	public void setLocale(Locale locale) {}

	@Override
	public Repository convert(String input,
			Class<? extends Repository> targetType, Collection<ValidationError> errors) {
		return dao.get(input);
	}

}
