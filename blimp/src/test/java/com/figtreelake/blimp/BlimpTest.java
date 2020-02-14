package com.figtreelake.blimp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.figtreelake.blimp.Blimp;
import com.figtreelake.blimp.BlimpRuntimeException;
import com.figtreelake.blimp.Message;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@RunWith(MockitoJUnitRunner.class)
public class BlimpTest {

  @Mock
  private MessageSource messageSource;

  private Blimp blimp;

  private static final Locale DEFAULT_LOCALE = Locale.getDefault();

  private static final Locale CUSTOMIZED_LOCALE = Locale.ENGLISH;

  private static final String TEST_DIRECTORY = BlimpTest.class.getSimpleName();

  private static final String CUSTOMIZED_MESSAGES_FILE_PATH = "classpath:" + TEST_DIRECTORY
      + File.separator + "custom-messages-directory" + File.separator + "custom-messages-file";

  private static final String CUSTOMIZED_SIMPLE_MESSAGE_CODE = "test.custom-simple-message";

  private static final String SIMPLE_MESSAGE_CODE = "test.simple-message";

  @BeforeClass
  public static void setUpClass() {
    Locale.setDefault(CUSTOMIZED_LOCALE);
  }

  @AfterClass
  public static void tearDownClass() {
    Locale.setDefault(DEFAULT_LOCALE);
  }

  @Before
  public void setUp() {
    this.blimp = new Blimp(messageSource);
  }

  @Test
  public void testGetMessageMessageObjectArray() throws Exception {

    // Arrange
    int firstParameter = 2658;
    String secondParameter = "drive";
    Object[] parameters = {firstParameter, secondParameter};
    Locale locale = Locale.getDefault();
    String messageCode = "messageCode";
    String expectedMessage = "This is a test message with parameters " + firstParameter + " and "
        + secondParameter + ".";

    Message mockMessage = mock(Message.class);

    when(mockMessage.getCode()).thenReturn(messageCode);
    when(messageSource.getMessage(eq(messageCode), eq(parameters), eq(locale)))
        .thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getMessage(mockMessage, firstParameter, secondParameter);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetMessageStringObjectArray() throws Exception {

    // Arrange
    int firstParameter = 2658;
    String secondParameter = "drive";
    Object[] parameters = {firstParameter, secondParameter};
    Locale locale = Locale.getDefault();
    String messageCode = "messageCode";
    String expectedMessage = "This is a test message with parameters " + firstParameter + " and "
        + secondParameter + ".";

    when(messageSource.getMessage(eq(messageCode), eq(parameters), eq(locale)))
        .thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getMessage(messageCode, firstParameter, secondParameter);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test(expected = BlimpRuntimeException.class)
  public void testGetMessageStringObjectArrayShouldThrowBlimpRuntimeExceptionWhenAnExceptionIsCaught()
      throws Exception {

    // Arrange
    int firstParameter = 2658;
    String secondParameter = "drive";
    Object[] parameters = {firstParameter, secondParameter};
    Locale locale = Locale.getDefault();
    String messageCode = "messageCode";

    when(messageSource.getMessage(eq(messageCode), eq(parameters), eq(locale)))
        .thenThrow(NoSuchMessageException.class);

    // Act
    blimp.getMessage(messageCode, firstParameter, secondParameter);

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanStringShouldReturnSingularMessageWhenParameterSingularIsTrue()
      throws Exception {
    // Arrange
    boolean singular = true;

    int firstSingularMessageParameter = 2658;
    String secondSingularMessageParameter = "drive";
    List<Object> singularMessageParametersList = Arrays
        .asList((Object) firstSingularMessageParameter, (Object) secondSingularMessageParameter);
    Object[] singularMessageParametersArray = singularMessageParametersList.toArray();

    double firstPluralMessageParameter = 3.14;
    long secondPluralMessageParameter = 1858206L;
    List<Object> pluralMessageParametersList =
        Arrays.asList(firstPluralMessageParameter, secondPluralMessageParameter);

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.SINGULAR_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message with parameters "
        + firstSingularMessageParameter + " and " + secondSingularMessageParameter + ".";

    when(messageSource.getMessage(eq(completeMessageCode), eq(singularMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, messageCodePrefix,
        singularMessageParametersList, pluralMessageParametersList);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanStringShouldReturnPluralMessageWhenParameterSingularIsFalse()
      throws Exception {
    // Arrange
    boolean singular = false;

    int firstSingularMessageParameter = 2658;
    String secondSingularMessageParameter = "drive";
    List<Object> singularMessageParametersList = Arrays
        .asList((Object) firstSingularMessageParameter, (Object) secondSingularMessageParameter);

    double firstPluralMessageParameter = 3.14;
    long secondPluralMessageParameter = 1858206L;
    List<Object> pluralMessageParametersList =
        Arrays.asList(firstPluralMessageParameter, secondPluralMessageParameter);
    Object[] pluralMessageParametersArray = pluralMessageParametersList.toArray();

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.PLURAL_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message with parameters " + firstPluralMessageParameter
        + " and " + secondPluralMessageParameter + ".";

    when(messageSource.getMessage(eq(completeMessageCode), eq(pluralMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, messageCodePrefix,
        singularMessageParametersList, pluralMessageParametersList);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanStringShouldReturnSingularMessageWhenSingularParameterIsTrue()
      throws Exception {

    // Arrange
    boolean singular = true;

    Object[] singularMessageParametersArray = null;

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.SINGULAR_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message.";

    when(messageSource.getMessage(eq(completeMessageCode), eq(singularMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, messageCodePrefix);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanStringShouldReturnPluralMessageWhenSingularParameterIsFalse()
      throws Exception {

    // Arrange
    boolean singular = false;

    Object[] pluralMessageParametersArray = null;

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.PLURAL_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message.";

    when(messageSource.getMessage(eq(completeMessageCode), eq(pluralMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, messageCodePrefix);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanMessageShouldReturnSingularMessageWhenSingularParameterIsTrue()
      throws Exception {
    // Arrange
    boolean singular = true;

    Object[] singularMessageParametersArray = null;

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.SINGULAR_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message.";

    Message mockMessage = mock(Message.class);

    when(mockMessage.getCode()).thenReturn(messageCodePrefix);
    when(messageSource.getMessage(eq(completeMessageCode), eq(singularMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, mockMessage);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanMessageShouldReturnPluralMessageWhenSingularParameterIsFalse()
      throws Exception {
    // Arrange
    boolean singular = false;

    Object[] pluralMessageParametersArray = null;

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.PLURAL_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message.";

    Message mockMessage = mock(Message.class);

    when(mockMessage.getCode()).thenReturn(messageCodePrefix);
    when(messageSource.getMessage(eq(completeMessageCode), eq(pluralMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, mockMessage);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanMessageObjectArrayObjectArrayShouldReturnSingularMessageWhenParameterSingularIsTrue()
      throws Exception {
    // Arrange
    boolean singular = true;

    int firstSingularMessageParameter = 2658;
    String secondSingularMessageParameter = "drive";
    List<Object> singularMessageParametersList = Arrays
        .asList((Object) firstSingularMessageParameter, (Object) secondSingularMessageParameter);
    Object[] singularMessageParametersArray = singularMessageParametersList.toArray();

    double firstPluralMessageParameter = 3.14;
    long secondPluralMessageParameter = 1858206L;
    List<Object> pluralMessageParametersList =
        Arrays.asList(firstPluralMessageParameter, secondPluralMessageParameter);

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.SINGULAR_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message with parameters "
        + firstSingularMessageParameter + " and " + secondSingularMessageParameter + ".";
    Message mockMessage = mock(Message.class);

    when(mockMessage.getCode()).thenReturn(messageCodePrefix);
    when(messageSource.getMessage(eq(completeMessageCode), eq(singularMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, mockMessage,
        singularMessageParametersList, pluralMessageParametersList);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testGetSingularOrPluralMessageBooleanMessageObjectArrayObjectArrayShouldReturnPluralMessageWhenParameterSingularIsFalse()
      throws Exception {
    // Arrange
    boolean singular = false;

    int firstSingularMessageParameter = 2658;
    String secondSingularMessageParameter = "drive";
    List<Object> singularMessageParametersList = Arrays
        .asList((Object) firstSingularMessageParameter, (Object) secondSingularMessageParameter);

    double firstPluralMessageParameter = 3.14;
    long secondPluralMessageParameter = 1858206L;
    List<Object> pluralMessageParametersList =
        Arrays.asList(firstPluralMessageParameter, secondPluralMessageParameter);
    Object[] pluralMessageParametersArray = pluralMessageParametersList.toArray();

    Locale locale = Locale.getDefault();

    String messageCodePrefix = "messageCodePrefix";
    String messageCodeSuffix = Blimp.PLURAL_SUFFIX;
    String completeMessageCode = messageCodePrefix + messageCodeSuffix;

    String expectedMessage = "This is a test message with parameters "
        + firstSingularMessageParameter + " and " + secondSingularMessageParameter + ".";
    Message mockMessage = mock(Message.class);

    when(mockMessage.getCode()).thenReturn(messageCodePrefix);
    when(messageSource.getMessage(eq(completeMessageCode), eq(pluralMessageParametersArray),
        eq(locale))).thenReturn(expectedMessage);

    // Act
    String actualMessage = blimp.getSingularOrPluralMessage(singular, mockMessage,
        singularMessageParametersList, pluralMessageParametersList);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  public void testBlimpLocaleStringArray() throws Exception {
    // Arrange
    Blimp blimp = new Blimp(CUSTOMIZED_LOCALE, CUSTOMIZED_MESSAGES_FILE_PATH);
    String expectedMessage = "This is a simple customized message.";

    // Act
    String actualMessage = blimp.getMessage(CUSTOMIZED_SIMPLE_MESSAGE_CODE);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);

  }

  @Test
  public void testBlimpListString() throws Exception {
    // Arrange
    List<String> messagesFilePaths = Arrays.asList(CUSTOMIZED_MESSAGES_FILE_PATH);
    Blimp blimp = new Blimp(messagesFilePaths);
    String expectedMessage = "This is a simple customized message.";

    // Act
    String actualMessage = blimp.getMessage(CUSTOMIZED_SIMPLE_MESSAGE_CODE);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test(expected = BlimpRuntimeException.class)
  public void testBlimpListStringShouldThrowBlimpRuntimeExceptionIfListStringParameterIsNull()
      throws Exception {
    // Arrange
    List<String> messagesFilePaths = null;

    // Act
    new Blimp(messagesFilePaths);

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test(expected = BlimpRuntimeException.class)
  public void testBlimpListStringShouldThrowBlimpRuntimeExceptionIfListStringParameterIsEmpty()
      throws Exception {
    // Arrange
    List<String> messagesFilePaths = new ArrayList<>();

    // Act
    new Blimp(messagesFilePaths);

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testBlimp() throws Exception {
    // Arrange
    Blimp blimp = new Blimp();
    String expectedMessage = "This is a simple message.";

    // Act
    String actualMessage = blimp.getMessage(SIMPLE_MESSAGE_CODE);

    // Assert
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

}
