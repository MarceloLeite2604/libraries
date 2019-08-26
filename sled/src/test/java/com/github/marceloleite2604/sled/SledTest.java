package com.github.marceloleite2604.sled;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.marceloleite2604.sled.Sled;
import com.github.marceloleite2604.sled.fixture.SledFixture;

public class SledTest {

	private Sled crypt;

	@Before
	public void setUp() {
		this.crypt = Sled.builder()
				.cryptographicAlgorythm(SledFixture.CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(SledFixture.FEEDBACK_MODE)
				.paddingScheme(SledFixture.PADDING_SCHEME)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildNullCryptographicAlgorithm() {
		Sled.builder()
				.cryptographicAlgorythm(null)
				.feedbackMode(SledFixture.FEEDBACK_MODE)
				.paddingScheme(SledFixture.PADDING_SCHEME)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildNullFeedbackMode() {
		Sled.builder()
				.cryptographicAlgorythm(SledFixture.CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(null)
				.paddingScheme(SledFixture.PADDING_SCHEME)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildNullPaddingScheme() {
		Sled.builder()
				.cryptographicAlgorythm(SledFixture.CRYPTOGRAPHIC_ALGORITHM)
				.feedbackMode(SledFixture.FEEDBACK_MODE)
				.paddingScheme(null)
				.build();
	}

	@Test
	public void testEncrypt() {
		String expectedResult = SledFixture.ENCRYPTED_TEXT;
		String actualResult = crypt.encrypt(SledFixture.TEXT, SledFixture.KEY);
		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testDecrypt() {
		String expectedResult = SledFixture.TEXT;
		String actualResult = crypt.decrypt(SledFixture.ENCRYPTED_TEXT, SledFixture.KEY);
		Assert.assertEquals(expectedResult, actualResult);
	}
}
