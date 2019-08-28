package com.github.marceloleite2604.blimp;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.CollectionUtils;

public class Blimp {

	static final String DEFAULT_MESSAGE_FILE_PATH = "classpath:i18n/messages/messages".replace("/",
			File.separator);
	
	static final Locale DEFAULT_LOCALE = Locale.getDefault();
	
	static final String SINGULAR_SUFFIX = ".singular";

	static final String PLURAL_SUFFIX = ".plural";

	private MessageSource messageSource;

	private Locale locale;

	public Blimp() {
		this(DEFAULT_LOCALE, DEFAULT_MESSAGE_FILE_PATH);
	}

	public Blimp(List<String> messagesFilePaths) {
		this(DEFAULT_LOCALE, messagesFilePaths);
	}

	public Blimp(Locale locale, List<String> messagesFilePaths) {
		this(locale, retrieveArrayFromMessageFilePathsList(messagesFilePaths));
	}

	public Blimp(Locale locale, String... messagesFilePaths) {
		this.messageSource = createMessageSource(messagesFilePaths);
		this.locale = locale;
	}
	
	public Blimp(MessageSource messageSource) {
		this(DEFAULT_LOCALE, messageSource);
	}

	public Blimp(Locale locale, MessageSource messageSource) {
		this.locale = locale;
		this.messageSource = messageSource;
	}



	private static String[] retrieveArrayFromMessageFilePathsList(List<String> messagesFilePaths) {
		if (CollectionUtils.isEmpty(messagesFilePaths)) {
			throw new BlimpRuntimeException(
					BlimpMessageTemplates.MESSAGES_FILE_PATHS_CANNOT_BE_NULL_OR_EMPTY);
		}
		return messagesFilePaths.toArray(new String[messagesFilePaths.size()]);
	}

	private MessageSource createMessageSource(String[] messagesFilePaths) {
		ReloadableResourceBundleMessageSource newMessageSource = new ReloadableResourceBundleMessageSource();
		newMessageSource.setBasenames(messagesFilePaths);
		newMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return newMessageSource;
	}

	public String getMessage(Message message, Object... parameters) {
		return getMessage(message.getCode(), parameters);
	}

	public String getMessage(String code, Object... parameters) {
		try {
			return messageSource.getMessage(code, parameters, locale);
		} catch (Exception exception) {
			String message = String.format(BlimpMessageTemplates.ERROR_RETRIEVING_MESSAGE, code);
			throw new BlimpRuntimeException(message, exception);
		}
	}

	public String getSingularOrPluralMessage(boolean singular, Message message) {
		return getSingularOrPluralMessage(singular, message.getCode());
	}

	public String getSingularOrPluralMessage(boolean singular, String codePrefix) {
		return getSingularOrPluralMessage(singular, codePrefix, null, null);
	}

	public String getSingularOrPluralMessage(boolean singular, Message message,
			List<Object> singularParameters, List<Object> pluralParameters) {
		return getSingularOrPluralMessage(singular, message.getCode(), singularParameters,
				pluralParameters);
	}

	public String getSingularOrPluralMessage(boolean singular, String codePrefix,
			List<Object> singularParameters, List<Object> pluralParameters) {

		String code = codePrefix.concat(singular ? SINGULAR_SUFFIX : PLURAL_SUFFIX);

		List<Object> parametersList = singular ? singularParameters : pluralParameters;

		Object[] parameters = null;

		if (!Objects.isNull(parametersList)) {
			parameters = parametersList.toArray();
		}

		return getMessage(code, parameters);
	}
}