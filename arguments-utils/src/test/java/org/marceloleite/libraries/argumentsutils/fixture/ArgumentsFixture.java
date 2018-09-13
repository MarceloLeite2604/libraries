package org.marceloleite.libraries.argumentsutils.fixture;

import java.util.Arrays;

public class ArgumentsFixture {

	public static final String ARGUMENT_A = "-a";
	public static final String VALUE_ARGUMENT_A = "valueA";
	public static final String ARGUMENT_B = "-b";
	public static final String ARGUMENT_C = "-c";
	public static final String VALUE_ARGUMENT_C = "valueC";
	public static final String ARGUMENT_D = "-d";
	public static final String INEXISTANT_ARGUMENT = "-x";

	public static String[] create() {
		return (String[]) Arrays
				.asList(ARGUMENT_A, VALUE_ARGUMENT_A, ARGUMENT_B, ARGUMENT_C, VALUE_ARGUMENT_C, ARGUMENT_D)
				.toArray();
	}
}
