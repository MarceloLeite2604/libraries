package com.github.marceloleite2604.crypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.github.marceloleite2604.crypt.exception.CryptException;

public final class Crypt {

	private Optional<String> keyEnvironmentVariableName;

	private String cryptographicAlgorythm;

	private String feedbackMode;

	private String paddingScheme;

	private String transformation;

	private Crypt(Builder builder) {

		this.keyEnvironmentVariableName = builder.keyEnvironmentVariableName;

		this.cryptographicAlgorythm = Optional.ofNullable(builder.cryptographicAlgorithm)
				.orElseThrow(() -> new IllegalArgumentException("Cryptographic algorithm cannot be null."));

		this.feedbackMode = Optional.ofNullable(builder.feedbackMode)
				.orElseThrow(() -> new IllegalArgumentException("Feedback mode cannot be null."));

		this.paddingScheme = Optional.ofNullable(builder.paddingScheme)
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
			throw new CryptException("Error while encrypting content.", exception);
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
			throw new CryptException("Error while decrypting content.", exception);
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

		String environmentVariableName = keyEnvironmentVariableName
				.orElseThrow(() -> new IllegalStateException("Key environment variable name was not informed."));

		String key = Optional.of(System.getenv(environmentVariableName))
				.orElseThrow(() -> new IllegalStateException(
						"Could not find encrypt key environment variable \"" + environmentVariableName + "\"."));

		if (key.isEmpty()) {
			throw new IllegalStateException("The encrypt key is empty.");
		}

		return key;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Optional<String> keyEnvironmentVariableName = Optional.empty();
		private String cryptographicAlgorithm;
		private String feedbackMode;
		private String paddingScheme;

		private Builder() {
		}

		public Builder keyEnvironmentVariableName(Optional<String> keyEnvironmentVariableName) {
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

		public Crypt build() {
			return new Crypt(this);
		}
	}
}
