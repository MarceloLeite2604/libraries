package com.github.marceloleite2604.sled.key;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KeyGeneratorTest {

  public static final String CRYPTOGRAPHIC_ALGORITHM = "DESede";

  private KeyGenerator keyGenerator;

  @Before
  public void setUp() {
    keyGenerator = new KeyGenerator();
  }

  @Test
  public void testGenerateKey() {
    String key = keyGenerator.generate(CRYPTOGRAPHIC_ALGORITHM);
    Assert.assertNotNull(key);
  }

  @Test(expected = KeyGeneratorRuntimeException.class)
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
