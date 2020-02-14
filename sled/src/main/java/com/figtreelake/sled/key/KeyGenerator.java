package com.figtreelake.sled.key;

import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class KeyGenerator {

  /**
   * Generates a key for the cryptographic algorithm informed.
   * 
   * @param cryptographicAlgorithm The cryptographic algorithm for which a key should be generated.
   * @return A key to use for encrypt and decrypt content for the cryptographic algorithm informed.
   */
  public String generate(String cryptographicAlgorithm) {
    if (cryptographicAlgorithm == null || cryptographicAlgorithm.isEmpty()) {
      throw new IllegalArgumentException(
          KeyGeneratorMessageTemplates.CRYPTOGRAPHIC_ALGORITHM_CANNOT_BE_NULL);
    }

    try {
      javax.crypto.KeyGenerator keygen =
          javax.crypto.KeyGenerator.getInstance(cryptographicAlgorithm);
      return DatatypeConverter.printBase64Binary(keygen.generateKey().getEncoded());
    } catch (NoSuchAlgorithmException exception) {
      throw new KeyGeneratorRuntimeException(
          KeyGeneratorMessageTemplates.ERROR_RETRIEVING_ENCRYPTION_ALGORIHTM, exception);
    }
  }

}
