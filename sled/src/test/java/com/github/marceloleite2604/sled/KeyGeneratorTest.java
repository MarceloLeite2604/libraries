package com.github.marceloleite2604.sled;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.marceloleite2604.sled.KeyGenerator;
import com.github.marceloleite2604.sled.exception.KeyGeneratorException;
import com.github.marceloleite2604.sled.fixture.SledFixture;


public class KeyGeneratorTest {

	private KeyGenerator keyGenerator;

	@Before
	public void setUp() {
		keyGenerator = new KeyGenerator();
	}

	@Test
	public void testGenerateKey() {
		String key = keyGenerator.generate(SledFixture.CRYPTOGRAPHIC_ALGORITHM);
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
