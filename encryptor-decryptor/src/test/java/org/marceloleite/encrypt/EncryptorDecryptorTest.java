package org.marceloleite.encrypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.marceloleite.libs.encrypt.EncryptorDecryptor;
import org.marceloleite.libs.encrypt.KeyGenerator;

public class EncryptorDecryptorTest {

	private static final String CRYPTOGRAPHIC_ALGORYTHM = "DESede";

	private EncryptorDecryptor encryptorDecryptor;
	
	private String key;

	@Before
	public void setUp() {
		this.encryptorDecryptor = EncryptorDecryptor.builder()
				.cryptographicAlgorythm(CRYPTOGRAPHIC_ALGORYTHM)
				.feedbackMode("CBC")
				.paddingScheme("PKCS5Padding")
				.build();
		
		this.key = new KeyGenerator().generateKey(CRYPTOGRAPHIC_ALGORYTHM);
	}
	
	@Test
	public void testEncryptDecrypt() {
		String text = "This text must be encrypted.";
		String decryptedText = encryptorDecryptor.decrypt(encryptorDecryptor.encrypt(text, key), key);
		Assert.assertEquals(text, decryptedText);
	}
}
