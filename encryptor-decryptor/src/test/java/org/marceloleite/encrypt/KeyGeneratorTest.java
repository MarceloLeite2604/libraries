package org.marceloleite.encrypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.marceloleite.libs.encrypt.KeyGenerator;
import org.marceloleite.libs.encrypt.exception.KeyGeneratorException;


public class KeyGeneratorTest {

	private KeyGenerator keyGenerator;

	@Before
	public void setUp() {
		keyGenerator = new KeyGenerator();
	}

	@Test
	public void testGenerateKey() {
		String key = keyGenerator.generateKey("DESede");
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
