package com.figtreelake.blimp;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.CollectionUtils;

/**
 * Blimp is a Basic Library for Internationalized Messages Pursuit. It helps retrieving
 * internationalized messages from properties files.
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class Blimp {

  /**
   * Default path to retrieve its properties files.
   */
  static final String DEFAULT_MESSAGE_FILE_PATH =
      "classpath:i18n/messages/messages".replace("/", File.separator);

  /**
   * Default locale for which the messages will be retrieved is system default.
   */
  static final Locale DEFAULT_LOCALE = Locale.getDefault();

  static final String SINGULAR_SUFFIX = ".singular";

  static final String PLURAL_SUFFIX = ".plural";

  private MessageSource messageSource;

  private Locale locale;

  /**
   * Constructs a Blimp object, retrieving message files from
   * {@link Blimp#DEFAULT_MESSAGE_FILE_PATH} for {@link Blimp#DEFAULT_LOCALE}.
   */
  public Blimp() {
    this(DEFAULT_LOCALE, DEFAULT_MESSAGE_FILE_PATH);
  }

  /**
   * Constructs a Blimp object, retrieving message properties from file paths informed on
   * {@code messagesFilePaths} using {@link Blimp#DEFAULT_LOCALE} locale.
   * 
   * @param messagesFilePaths A list of file paths to search for message properties.
   */
  public Blimp(List<String> messagesFilePaths) {
    this(DEFAULT_LOCALE, messagesFilePaths);
  }

  /**
   * Constructs a Blimp object, retrieving message properties from file paths informed on
   * {@code messagesFilePaths} using locale informed on {@code locale} parameter.
   * 
   * @param locale Locale which will be used to retrieve message properties.
   * @param messagesFilePaths A list of file paths to search for message properties.
   */
  public Blimp(Locale locale, List<String> messagesFilePaths) {
    this(locale, retrieveArrayFromMessageFilePathsList(messagesFilePaths));
  }

  /**
   * Constructs a Blimp object, retrieving message properties from file paths informed on
   * {@code messagesFilePaths} using locale informed on {@code locale} parameter.
   * 
   * @param locale Locale which will be used to retrieve message properties.
   * @param messagesFilePaths File paths to search for message properties.
   */
  public Blimp(Locale locale, String... messagesFilePaths) {
    this.messageSource = createMessageSource(messagesFilePaths);
    this.locale = locale;
  }

  /**
   * Constructs a Blimp object, retrieving messages from {@link MessageSource} informed on
   * {@code messageSource} parameter using {@link Blimp#DEFAULT_LOCALE} locale.
   * 
   * @param messageSource The message source used for messages retrieval.
   */
  public Blimp(MessageSource messageSource) {
    this(DEFAULT_LOCALE, messageSource);
  }

  /**
   * Constructs a Blimp object, retrieving messages from {@link MessageSource} informed on
   * {@code messageSource} parameter using locale informed on {@code locale} parameter.
   * 
   * @param locale Locale which will be used to retrieve message properties.
   * @param messageSource The message source used for messages retrieval.
   */
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
    ReloadableResourceBundleMessageSource newMessageSource =
        new ReloadableResourceBundleMessageSource();
    newMessageSource.setBasenames(messagesFilePaths);
    newMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    return newMessageSource;
  }

  /**
   * Retrieves a message from one of the message files.
   * 
   * @param message {@link Message} object which contains the message code.
   * @param parameters Parameters to inform on message elaboration.
   * @return The message retrieved filled with the parameters informed.
   */
  public String getMessage(Message message, Object... parameters) {
    return getMessage(message.getCode(), parameters);
  }

  /**
   * Retrieves a message from one of the message files.
   * 
   * @param code The message code to be retrieved.
   * @param parameters Parameters to inform on message elaboration.
   * @return The message retrieved filled with the parameters informed.
   */
  public String getMessage(String code, Object... parameters) {
    try {
      return this.messageSource.getMessage(code, parameters, this.locale);
    } catch (Exception exception) {
      String message = String.format(BlimpMessageTemplates.ERROR_RETRIEVING_MESSAGE, code);
      throw new BlimpRuntimeException(message, exception);
    }
  }

  /**
   * Retrieves either the singular or plural equivalent of a message.
   * 
   * @param singular When {@code true} the singular message will be retrieved. Otherwise, its plural
   *        equivalent will be used.
   * @param message {@link Message} object which contains the message code.
   * @return The message retrieved filled with the parameters informed.
   */
  public String getSingularOrPluralMessage(boolean singular, Message message) {
    return getSingularOrPluralMessage(singular, message.getCode());
  }

  /**
   * Retrieves either the singular or plural equivalent of a message.
   * 
   * @param singular When {@code true} the singular message will be retrieved. Otherwise, its plural
   *        equivalent will be used.
   * @param messageCode The message code to be retrieved.
   * @return The message retrieved filled with the parameters informed.
   */
  public String getSingularOrPluralMessage(boolean singular, String messageCode) {
    return getSingularOrPluralMessage(singular, messageCode, null, null);
  }

  /**
   * Retrieves either the singular or plural equivalent of a message.
   * 
   * @param singular When {@code true} the singular message will be retrieved. Otherwise, its plural
   *        equivalent will be used.
   * @param message {@link Message} object which contains the message code.
   * @param singularParameters Parameters to be used when a singular message will be elaborated.
   * @param pluralParameters Parameters to be used when a plural message will be elaborated.
   * @return The message retrieved filled with the parameters informed.
   */
  public String getSingularOrPluralMessage(boolean singular, Message message,
      List<Object> singularParameters, List<Object> pluralParameters) {
    return getSingularOrPluralMessage(singular, message.getCode(), singularParameters,
        pluralParameters);
  }

  /**
   * Retrieves either the singular or plural equivalent of a message.
   * 
   * @param singular When {@code true} the singular message will be retrieved. Otherwise, its plural
   *        equivalent will be used.
   * @param messageCode The message code to be retrieved.
   * @param singularParameters Parameters to be used when a singular message will be elaborated.
   * @param pluralParameters Parameters to be used when a plural message will be elaborated.
   * @return The message retrieved filled with the parameters informed.
   */
  public String getSingularOrPluralMessage(boolean singular, String messageCode,
      List<Object> singularParameters, List<Object> pluralParameters) {

    String code = messageCode.concat(singular ? SINGULAR_SUFFIX : PLURAL_SUFFIX);

    List<Object> parametersList = singular ? singularParameters : pluralParameters;

    Object[] parameters = null;

    if (!Objects.isNull(parametersList)) {
      parameters = parametersList.toArray();
    }

    return getMessage(code, parameters);
  }

}
