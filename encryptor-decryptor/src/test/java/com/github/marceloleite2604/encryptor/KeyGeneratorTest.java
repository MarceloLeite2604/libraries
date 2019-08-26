package com.github.marceloleite2604.encryptor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.marceloleite2604.encryptor.exception.KeyGeneratorRuntimeException;


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

	@Test(expected = KeyGeneratorRuntimeException.class)
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
