package com.github.marceloleite2604.sled;

public final class SledMessageTemplates {

	public static final String CRYPTOGRAPHIC_ALGORITHM_CANNOT_BE_NULL = "Cryptographic algorithm cannot be null.";

	public static final String FEEDBACK_MODE_CANNOT_BE_NULL = "Feedback mode cannot be null.";

	public static final String PADDING_SCHEME_CANNOT_BE_NULL = "Paddding scheme cannot be null.";

	public static final String KEY_ENVIRONMENT_VARIABLE_WAS_NOT_INFORMED = "Key environment variable name was not informed.";

	public static final String COULD_NOT_FIND_ENCRYPT_KEY_ENVIRONMENT_VARIABLE = "Could not find encrypt key environment variable \"%s\".";

	public static final String ENCRYPT_KEY_IS_EMPTY = "The encrypt key is empty.";
	
	public static final String ERROR_RETRIEVING_ENCRYPTION_ALGORIHTM = "An error occurred while retrieving encryption algorithm.";

	private SledMessageTemplates() {
		// Private constructor to avoid object instantiation.
	}
}
