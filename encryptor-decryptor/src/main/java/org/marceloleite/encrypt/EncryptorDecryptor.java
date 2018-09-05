package org.marceloleite.encrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.marceloleite.encrypt.exception.DecryptionRuntimeException;
import org.marceloleite.encrypt.exception.EncryptionRuntimeException;

public final class EncryptorDecryptor {

	private String keyEnvironmentVariableName;

	private String cryptographicAlgorythm;

	private String feedbackMode;

	private String paddingScheme;

	private String transformation;

	private EncryptorDecryptor(Builder builder) {

		this.keyEnvironmentVariableName = builder.keyEnvironmentVariableName;

		this.cryptographicAlgorythm = Optional.of(builder.cryptographicAlgorithm)
				.orElseThrow(() -> new IllegalArgumentException("Cryptographic algorithm cannot be null."));

		this.feedbackMode = Optional.of(builder.feedbackMode)
				.orElseThrow(() -> new IllegalArgumentException("Feedback mode cannot be null."));

		this.paddingScheme = Optional.of(builder.paddingScheme)
				.orElseThrow(() -> new IllegalArgumentException("Paddding scheme cannot be null."));

		this.transformation = cryptographicAlgorythm + "/" + feedbackMode + "/" + paddingScheme;
	}

	public String encrypt(String content) {
		return encrypt(content, retrieveKey());
	}

	public String encrypt(String content, String key) {
		try {
			byte[] keyBytes = DatatypeConverter.parseBase64Binary(key);
			byte[] cryptedBytes = encryptDecrypt(content.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
			return DatatypeConverter.printBase64Binary(cryptedBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException exception) {
			throw new EncryptionRuntimeException("Error while encrypting content.", exception);
		}
	}

	public String decrypt(String content) {
		return decrypt(content, retrieveKey());
	}

	public String decrypt(String content, String key) {
		try {
			byte[] encryptedBytes = DatatypeConverter.parseBase64Binary(content);
			byte[] keyBytes = DatatypeConverter.parseBase64Binary(key);
			byte[] decryptedBytes = encryptDecrypt(encryptedBytes, keyBytes, Cipher.DECRYPT_MODE);
			return new String(decryptedBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | InvalidAlgorithmParameterException exception) {
			throw new DecryptionRuntimeException("Error while decrypting content.", exception);
		}
	}

	private byte[] encryptDecrypt(byte[] content, byte[] key, int opMode)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		SecretKey secretKey = new SecretKeySpec(key, cryptographicAlgorythm);
		Cipher cipher = createCipher(secretKey, opMode);
		return cipher.doFinal(content);
	}

	private Cipher createCipher(final SecretKey secretKey, int opMode) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(opMode, secretKey, new IvParameterSpec(new byte[8]));
		return cipher;
	}

	private String retrieveKey() {

		if (keyEnvironmentVariableName == null) {
			throw new IllegalStateException("Key environment variable name was not informed.");
		}

		String key = System.getenv(keyEnvironmentVariableName);
		if (key == null) {
			throw new IllegalStateException(
					"Could not find encrypt key environment variable \"" + keyEnvironmentVariableName + "\".");
		}

		if (key.isEmpty()) {
			throw new IllegalStateException("The encrypt key is empty.");
		}

		return key;
	}

	public String generateKey(String cryptograhgicAlgorithm) {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance(cryptograhgicAlgorithm);
			return DatatypeConverter.printBase64Binary(keygen.generateKey()
					.getEncoded());
		} catch (NoSuchAlgorithmException exception) {
			throw new EncryptionRuntimeException("Error while retrieving encryption algorythm.", exception);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String keyEnvironmentVariableName;
		private String cryptographicAlgorithm;
		private String feedbackMode;
		private String paddingScheme;

		private Builder() {
		}

		public Builder keyEnvironmentVariableName(String keyEnvironmentVariableName) {
			this.keyEnvironmentVariableName = keyEnvironmentVariableName;
			return this;
		}

		public Builder cryptographicAlgorythm(String cryptographicAlgorythm) {
			this.cryptographicAlgorithm = cryptographicAlgorythm;
			return this;
		}

		public Builder feedbackMode(String feedbackMode) {
			this.feedbackMode = feedbackMode;
			return this;
		}

		public Builder paddingScheme(String paddingScheme) {
			this.paddingScheme = paddingScheme;
			return this;
		}

		public EncryptorDecryptor build() {
			return new EncryptorDecryptor(this);
		}
	}
}
