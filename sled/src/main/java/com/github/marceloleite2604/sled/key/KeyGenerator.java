package com.github.marceloleite2604.sled.key;

import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.github.marceloleite2604.sled.SledMessageTemplates;

public class KeyGenerator {

	public String generate(String cryptographicAlgorithm) {
		if (cryptographicAlgorithm == null || cryptographicAlgorithm.isEmpty()) {
			throw new IllegalArgumentException(
					SledMessageTemplates.CRYPTOGRAPHIC_ALGORITHM_CANNOT_BE_NULL);
		}

		try {
			javax.crypto.KeyGenerator keygen = javax.crypto.KeyGenerator
					.getInstance(cryptographicAlgorithm);
			return DatatypeConverter.printBase64Binary(keygen.generateKey()
					.getEncoded());
		} catch (NoSuchAlgorithmException exception) {
			throw new KeyGeneratorRuntimeException(
					SledMessageTemplates.ERROR_RETRIEVING_ENCRYPTION_ALGORIHTM, exception);
		}
	}

}
