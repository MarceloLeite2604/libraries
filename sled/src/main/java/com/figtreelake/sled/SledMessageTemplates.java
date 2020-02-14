package com.figtreelake.sled;

final class SledMessageTemplates {

  static final String CRYPTOGRAPHIC_ALGORITHM_CANNOT_BE_NULL =
      "Cryptographic algorithm cannot be null.";

  static final String FEEDBACK_MODE_CANNOT_BE_NULL = "Feedback mode cannot be null.";

  static final String PADDING_SCHEME_CANNOT_BE_NULL = "Paddding scheme cannot be null.";

  static final String KEY_ENVIRONMENT_VARIABLE_WAS_NOT_INFORMED =
      "Key environment variable name was not informed.";

  static final String COULD_NOT_FIND_ENCRYPT_KEY_ENVIRONMENT_VARIABLE =
      "Could not find encrypt key environment variable \"%s\".";

  static final String ENCRYPT_KEY_IS_EMPTY = "The encrypt key is empty.";

  private SledMessageTemplates() {
    // Private constructor to avoid object instantiation.
  }

}
