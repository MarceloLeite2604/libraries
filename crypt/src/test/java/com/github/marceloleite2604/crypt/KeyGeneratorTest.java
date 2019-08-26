package com.github.marceloleite2604.crypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.marceloleite2604.crypt.KeyGenerator;
import com.github.marceloleite2604.crypt.exception.KeyGeneratorException;
import com.github.marceloleite2604.crypt.fixture.CryptFixture;


public class KeyGeneratorTest {

	private KeyGenerator keyGenerator;

	@Before
	public void setUp() {
		keyGenerator = new KeyGenerator();
	}

	@Test
	public void testGenerateKey() {
		String key = keyGenerator.generate(CryptFixture.CRYPTOGRAPHIC_ALGORITHM);
		Assert.assertNotNull(key);
	}

	@Test(expected = KeyGeneratorException.class)
	public void testGenerateKeyUnknownCryptographicAlgorithm() {
		keyGenerator.generate("pijiovhnudfb");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyEmptyCryptographicAlgorithm() {
		keyGenerator.generate("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateKeyNullCryptographicAlgorithm() {
		keyGenerator.generate(null);
	}

}
