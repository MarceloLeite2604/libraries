package org.marceloleite.libs.crypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.marceloleite.libs.crypt.KeyGenerator;
import org.marceloleite.libs.crypt.exception.KeyGeneratorException;
import org.marceloleite.libs.crypt.fixture.CryptFixture;


public class KeyGeneratorTest {

	private KeyGenerator keyGenerator;

	@Before
	public void setUp() {
		keyGenerator = new KeyGenerator();
	}

	@Test
	public void testGenerateKey() {
		String key = keyGenerator.generateKey(CryptFixture.CRYPTOGRAPHIC_ALGORITHM);
		Assert.assertNotNull(key);
	}

	@Test(expected = KeyGeneratorException.class)
	public void testGenerateKeyUnknownCryptographicAlgorithm() {
		keyGenerator.generateKey("pijiovhnudfb");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyEmptyCryptographicAlgorithm() {
		keyGenerator.generateKey("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyNullCryptographicAlgorithm() {
		keyGenerator.generateKey(null);
	}

}
