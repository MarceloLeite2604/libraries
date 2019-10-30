package com.github.marceloleite2604.util.fixture;

import java.util.Arrays;

public class ArgumentsFixture {

  public static final String ARGUMENT_1 = "-a";

  public static final String VALUE_ARGUMENT_1 = "valueA";

  public static final String ARGUMENT_2 = "-b";

  public static final String ARGUMENT_3 = "-c";

  public static final String VALUE_ARGUMENT_3 = "valueC";

  public static final String ARGUMENT_4 = "-d";

  public static final String INEXISTENT_ARGUMENT = "-x";

  public static final String[] ARGUMENTS =
      {ARGUMENT_1, VALUE_ARGUMENT_1, ARGUMENT_2, ARGUMENT_3, VALUE_ARGUMENT_3, ARGUMENT_4};

  public static String[] create() {
    return Arrays.copyOf(ARGUMENTS, ARGUMENTS.length);
  }

}
