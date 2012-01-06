package sgit;

import java.util.Locale;

import com.google.inject.Injector;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.validation.DefaultTypeConverterFactory;
import net.sourceforge.stripes.validation.TypeConverter;

public class TypeConverterFactory extends DefaultTypeConverterFactory {
	private Injector injector;

	@Override
	public void init(Configuration configuration) {		
		super.init(configuration);
		injector = (Injector) configuration.getServletContext().getAttribute(Injector.class.getName());
	}

	@Override
	public TypeConverter getInstance(Class<? extends TypeConverter> clazz,
			Locale locale) throws Exception {		
		TypeConverter converter = injector.getInstance(clazz);
		converter.setLocale(locale);
		return converter;
	}

}
