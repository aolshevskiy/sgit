package sgit.ext;

import java.util.Collection;
import java.util.Locale;

import com.google.inject.Inject;

import sgit.dao.RepositoryDao;
import sgit.dto.SRepository;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class RepositoryTypeConverter implements TypeConverter<SRepository> {
	private final RepositoryDao dao;
	
	@Inject
	RepositoryTypeConverter(RepositoryDao dao) {
		this.dao = dao;
	}

	@Override
	public void setLocale(Locale locale) {}

	@Override
	public SRepository convert(String input,
			Class<? extends SRepository> targetType, Collection<ValidationError> errors) {
		return dao.get(input);
	}

}
