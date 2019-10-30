package com.github.marceloleite2604.sled;

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

/**
 * <p>
 * Sled is Simple Library for Encryption and Decryption. It helps encrypt and decrypt texts using
 * Java's most known transformations.
 * </p>
 * <p>
 * The transformation is a composition of a cryptographic algorithm, a feedback mode and a padding
 * mode. Also, a key is utilized for these transformations. Sled allows the key to be informed
 * during the transformation request or during its initialization, which will make the same key be
 * used whenever an transformation is requested. Lastly, it is also possible to inform an
 * environment variable name from which Sled will retrieve the transformation key.
 * </p>
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 */
public class Sled {

  private Optional<String> keyEnvironmentVariableName;

  private String cryptographicAlgorithm;

  private String feedbackMode;

  private String paddingScheme;

  private String transformation;

  private Sled(Builder builder) {

    this.keyEnvironmentVariableName = builder.keyEnvironmentVariableName;

    this.cryptographicAlgorithm = Optional.ofNullable(builder.cryptographicAlgorithm)
        .orElseThrow(() -> new SledRuntimeException(
            SledMessageTemplates.CRYPTOGRAPHIC_ALGORITHM_CANNOT_BE_NULL));

    this.feedbackMode = Optional.ofNullable(builder.feedbackMode).orElseThrow(
        () -> new SledRuntimeException(SledMessageTemplates.FEEDBACK_MODE_CANNOT_BE_NULL));

    this.paddingScheme = Optional.ofNullable(builder.paddingScheme).orElseThrow(
        () -> new SledRuntimeException(SledMessageTemplates.PADDING_SCHEME_CANNOT_BE_NULL));

    this.transformation = cryptographicAlgorithm + "/" + feedbackMode + "/" + paddingScheme;
  }

  /**
   * Encrypts a text either using the key informed as parameter or stored on the environment
   * variable informed during this object instantiation.
   * 
   * @param text Text to be encrypted.
   * @return The content of {@code text} parameter encrypted.
   */
  public String encrypt(String text) {
    return encrypt(text, retrieveKey());
  }

  /**
   * Encrypts a text using the key informed .
   * 
   * @param text Text to be encrypted.
   * @param key Key to use during the encryption.
   * @return The content of {@code text} parameter encrypted.
   */
  public String encrypt(String text, String key) {
    try {
      byte[] keyBytes = DatatypeConverter.parseBase64Binary(key);
      byte[] cryptedBytes = encryptDecrypt(text.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
      return DatatypeConverter.printBase64Binary(cryptedBytes);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
        | IllegalBlockSizeException | BadPaddingException
        | InvalidAlgorithmParameterException exception) {
      throw new SledRuntimeException("Error while encrypting content.", exception);
    }
  }

  /**
   * Decrypts a content either using the key informed as parameter or stored on the environment
   * variable informed during this object instantiation.
   * 
   * @param content The content to be decrypted.
   * @return The content of {@code text} parameter decrypted.
   */
  public String decrypt(String content) {
    return decrypt(content, retrieveKey());
  }

  /**
   * Decrypts a content using the key informed .
   * 
   * @param content Content to be decrypted.
   * @param key Key to use during the decryption.
   * @return The content of {@code content} parameter decrypted.
   */
  public String decrypt(String content, String key) {
    try {
      byte[] encryptedBytes = DatatypeConverter.parseBase64Binary(content);
      byte[] keyBytes = DatatypeConverter.parseBase64Binary(key);
      byte[] decryptedBytes = encryptDecrypt(encryptedBytes, keyBytes, Cipher.DECRYPT_MODE);
      return new String(decryptedBytes);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
        | IllegalBlockSizeException | BadPaddingException
        | InvalidAlgorithmParameterException exception) {
      throw new SledRuntimeException("Error while decrypting content.", exception);
    }
  }

  private byte[] encryptDecrypt(byte[] content, byte[] key, int opMode)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
      IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
    SecretKey secretKey = new SecretKeySpec(key, cryptographicAlgorithm);
    Cipher cipher = createCipher(secretKey, opMode);
    return cipher.doFinal(content);
  }

  private Cipher createCipher(final SecretKey secretKey, int opMode)
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
      InvalidAlgorithmParameterException {
    Cipher cipher = Cipher.getInstance(transformation);
    cipher.init(opMode, secretKey, new IvParameterSpec(new byte[8]));
    return cipher;
  }

  private String retrieveKey() {

    String environmentVariableName =
        keyEnvironmentVariableName.orElseThrow(() -> new SledRuntimeException(
            SledMessageTemplates.KEY_ENVIRONMENT_VARIABLE_WAS_NOT_INFORMED));
    String key = Optional.ofNullable(System.getenv(environmentVariableName)).orElseThrow(() -> {
      String message =
          String.format(SledMessageTemplates.COULD_NOT_FIND_ENCRYPT_KEY_ENVIRONMENT_VARIABLE,
              environmentVariableName);
      return new SledRuntimeException(message);
    });

    if (key.isEmpty()) {
      throw new SledRuntimeException(SledMessageTemplates.ENCRYPT_KEY_IS_EMPTY);
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

    private Builder() {}

    public Builder keyEnvironmentVariableName(String keyEnvironmentVariableName) {
      this.keyEnvironmentVariableName = Optional.ofNullable(keyEnvironmentVariableName);
      return this;
    }

    public Builder cryptographicAlgorithm(String cryptographicAlgorithm) {
      this.cryptographicAlgorithm = cryptographicAlgorithm;
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

    public Sled build() {
      return new Sled(this);
    }

  }

}
