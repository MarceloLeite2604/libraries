package com.github.marceloleite2604.properties;

import java.util.Optional;
import java.util.Properties;

import com.github.marceloleite2604.properties.exception.PropertyRuntimeException;
import com.github.marceloleite2604.properties.model.Property;

public final class PropertyUtil {

	private PropertyUtil() {
		// Private constructor to avoid object instantiation.
	}

	public static String retrieve(Property property, Properties properties, String defaultValue) {
		Optional<String> optionalPropertyValue = Optional.ofNullable(properties.getProperty(property.getName()));

		if (optionalPropertyValue.isPresent()) {
			return optionalPropertyValue.get();
		} else {
			if (!property.isMandatory()) {
				return property.getDefaultValue();
			} else {
				if (defaultValue != null) {
					return defaultValue;
				} else {
					throw new PropertyRuntimeException("Unable to find property \"" + property.getName() + "\".");
				}
			}
		}
	}

	public static String retrieve(Property property, Properties properties) {
		return retrieve(property, properties, null);
	}

	public static void copy(Properties source, Property fromProperty, Properties destination, Property toProperty) {
		destination.put(toProperty.getName(), source.get(fromProperty.getName()));
	}

	public static Properties clone(Properties source) {

		Properties properties = new Properties();
		source.forEach(properties::put);
		return properties;
	}
}
