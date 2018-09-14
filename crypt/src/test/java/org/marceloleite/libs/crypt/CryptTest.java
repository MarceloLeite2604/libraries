package org.marceloleite.libs.crypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.marceloleite.libs.crypt.fixture.CryptFixture;

public class CryptTest {

	private Crypt crypt;

	@Before
	public void setUp() {
		this.crypt = Crypt.builder()
				.cryptographicAlgorythm(CryptFixture.CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(CryptFixture.FEEDBACK_MODE)
				.paddingScheme(CryptFixture.PADDING_SCHEME)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildNullCryptographicAlgorithm() {
		Crypt.builder()
				.cryptographicAlgorythm(null)
				.feedbackMode(CryptFixture.FEEDBACK_MODE)
				.paddingScheme(CryptFixture.PADDING_SCHEME)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildNullFeedbackMode() {
		Crypt.builder()
				.cryptographicAlgorythm(CryptFixture.CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(null)
				.paddingScheme(CryptFixture.PADDING_SCHEME)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildNullPaddingScheme() {
		Crypt.builder()
				.cryptographicAlgorythm(CryptFixture.CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(CryptFixture.FEEDBACK_MODE)
				.paddingScheme(null)
				.build();
	}

	@Test
	public void testEncrypt() {
		String expectedResult = CryptFixture.ENCRYPTED_TEXT;
		String actualResult = crypt.encrypt(CryptFixture.TEXT, CryptFixture.KEY);
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testDecrypt() {
		String expectedResult = CryptFixture.TEXT;
		String actualResult = crypt.decrypt(CryptFixture.ENCRYPTED_TEXT, CryptFixture.KEY);
		Assert.assertEquals(expectedResult, actualResult);
	}
}
