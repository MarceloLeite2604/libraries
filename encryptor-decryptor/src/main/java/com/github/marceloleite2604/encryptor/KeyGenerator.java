package com.github.marceloleite2604.encryptor;

import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.github.marceloleite2604.encryptor.exception.KeyGeneratorRuntimeException;

public class KeyGenerator {
	
	public String generateKey(String cryptographicAlgorithm) {
		if ( cryptographicAlgorithm == null || cryptographicAlgorithm.isEmpty() ) {
			throw new IllegalArgumentException("Cryptographic algorythm cannot be null.");
		}
		
		try {
			javax.crypto.KeyGenerator keygen = javax.crypto.KeyGenerator.getInstance(cryptographicAlgorithm);
			return DatatypeConverter.printBase64Binary(keygen.generateKey()
					.getEncoded());
		} catch (NoSuchAlgorithmException exception) {
			throw new KeyGeneratorRuntimeException("Error while retrieving encryption algorythm.", exception);
		}
	}

	
}
