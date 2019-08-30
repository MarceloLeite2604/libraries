package com.github.marceloleite2604.sled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.agent.PowerMockAgent;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest({ Sled.class, System.class })
public class SledTest {

	public static final String CRYPTOGRAPHIC_ALGORITHM = "DESede";

	public static final String INVALID_CRYPTOGRAPHIC_ALGORITHM = "InvalidCryptographicAlgorithm";

	public static final String FEEDBACK_MODE = "CBC";

	public static final String PADDING_SCHEME = "PKCS5Padding";

	public static final String KEY_ENVIRONMENT_VARIABLE_NAME = "keyEnvironmentVariableName";

	public static final String KEY = "xNyryKQfcw5wZJ0shhAf9AdAueUmLKRk";

	public static final String TEXT = "This text must be encrypted.";

	public static final String ENCRYPTED_TEXT = "2HXoZJZuA72RgX9mImqOgK48po9ChdRAxsy+Sp3cKNA=";

	public static final String TRANSFORMATION = CRYPTOGRAPHIC_ALGORITHM + "/" + FEEDBACK_MODE + "/"
			+ PADDING_SCHEME;

	private Sled sled;

	@Rule
	public PowerMockRule rule = new PowerMockRule();

	static {
		PowerMockAgent.initializeIfNeeded();
	}

	@Before
	public void setUp() {
		this.sled = Sled.builder()
				.cryptographicAlgorithm(CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(FEEDBACK_MODE)
				.paddingScheme(PADDING_SCHEME)
				.keyEnvironmentVariableName(KEY_ENVIRONMENT_VARIABLE_NAME)
				.build();
	}

	@Test(expected = SledRuntimeException.class)
	public void buildNullCryptographicAlgorithm() {
		Sled.builder()
				.cryptographicAlgorithm(null)
				.feedbackMode(FEEDBACK_MODE)
				.paddingScheme(PADDING_SCHEME)
				.build();
	}

	@Test(expected = SledRuntimeException.class)
	public void buildNullFeedbackMode() {
		Sled.builder()
				.cryptographicAlgorithm(CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(null)
				.paddingScheme(PADDING_SCHEME)
				.build();
	}

	@Test(expected = SledRuntimeException.class)
	public void buildNullPaddingScheme() {
		Sled.builder()
				.cryptographicAlgorithm(CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(FEEDBACK_MODE)
				.paddingScheme(null)
				.build();
	}

	@Test
	public void testEncryptStringString() {
		String expectedResult = ENCRYPTED_TEXT;
		String actualResult = sled.encrypt(TEXT, KEY);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testDecryptStringString() {
		String expectedResult = TEXT;
		String actualResult = sled.decrypt(ENCRYPTED_TEXT, KEY);
		assertEquals(expectedResult, actualResult);
	}

	@Test(expected = SledRuntimeException.class)
	public void testDecryptStringStringShouldThrowSledRuntimeExceptionWhenNoSuchAlgorithmExceptionIsCaught() {
		// Arrange
		Sled sled = Sled.builder()
				.cryptographicAlgorithm(INVALID_CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(FEEDBACK_MODE)
				.paddingScheme(PADDING_SCHEME)
				.build();

		// Act
		sled.decrypt(TEXT, KEY);

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test(expected = SledRuntimeException.class)
	public void testDecryptStringShouldThrowSledRuntimeExceptionWhenNoKeyEnvironmentVariableWasInformed() {
		// Arrange
		Sled sled = Sled.builder()
				.cryptographicAlgorithm(INVALID_CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(FEEDBACK_MODE)
				.paddingScheme(PADDING_SCHEME)
				.build();

		// Act
		sled.decrypt(TEXT);

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testDecryptString() throws Exception {
		// Arrange
		String expectedDecryptedText = TEXT;

		mockStatic(System.class);
		when(System.getenv(KEY_ENVIRONMENT_VARIABLE_NAME)).thenReturn(KEY);

		// Act
		String actualDecrypteddText = sled.decrypt(ENCRYPTED_TEXT);

		// Assert
		assertThat(actualDecrypteddText).isEqualTo(expectedDecryptedText);
	}

	@Test(expected = SledRuntimeException.class)
	public void testDecryptStringShouldThrowSledRuntimeExceptionWhenEnvironmentVariableDoesNotExist()
			throws Exception {
		// Arrange
		mockStatic(System.class);
		when(System.getenv(KEY_ENVIRONMENT_VARIABLE_NAME)).thenReturn(null);

		// Act
		sled.decrypt(ENCRYPTED_TEXT);

		// Assert
		fail("Should have thrown an exception.");
	}
	
	@Test(expected = SledRuntimeException.class)
	public void testDecryptStringShouldThrowSledRuntimeExceptionWhenEnvironmentVariableIsEmpty()
			throws Exception {
		// Arrange
		mockStatic(System.class);
		when(System.getenv(KEY_ENVIRONMENT_VARIABLE_NAME)).thenReturn("");

		// Act
		sled.decrypt(ENCRYPTED_TEXT);

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testEncryptString() throws Exception {
		// Arrange
		String expectedEncryptedText = ENCRYPTED_TEXT;

		mockStatic(System.class);
		when(System.getenv(KEY_ENVIRONMENT_VARIABLE_NAME)).thenReturn(KEY);

		// Act
		String actualEncryptedText = sled.encrypt(TEXT);

		// Assert
		assertThat(actualEncryptedText).isEqualTo(expectedEncryptedText);
	}

	@Test(expected = SledRuntimeException.class)
	public void testEncryptStringStringShouldThrowSledRuntimeExceptionWhenNoSuchAlgorithmExceptionIsCaught()
			throws Exception {

		// Arrange
		Sled sled = Sled.builder()
				.cryptographicAlgorithm(INVALID_CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(FEEDBACK_MODE)
				.paddingScheme(PADDING_SCHEME)
				.build();

		// Act
		sled.encrypt(TEXT, KEY);

		// Assert
		fail("Should have thrown an exception.");
	}
}
