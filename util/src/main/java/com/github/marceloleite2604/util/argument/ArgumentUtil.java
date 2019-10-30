package com.github.marceloleite2604.util.argument;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * A series of handy methods which helps manipulating arguments.
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class ArgumentUtil {

  private static final String PARAMETER_NAME_REGEX = "-[^-\\s]+";

  /**
   * Retrieves the value of an optional argument.
   * 
   * @param arguments the arguments array.
   * @param name name of the argument to be retrieved.
   * @return an {@link Optional} containing the argument value. If no argument was found, returns an
   *         empty {@link Optional}.
   */
  public Optional<String> retrieveArgument(String[] arguments, String name) {
    String argument = retrieveArgument(arguments, name, null);
    if (argument == null) {
      return Optional.empty();
    } else {
      return Optional.of(argument);
    }
  }

  /**
   * Retrieves the value of an optional argument. If no value was found, returns the value informed
   * on {@code defaultValue} parameter.
   * 
   * @param arguments The arguments array.
   * @param name Name of the argument to be retrieved.
   * @param defaultValue Default value to be returned if the parameter is not found.
   * @return If the argument was found, the method returns its value. Otherwise, it will return the
   *         {@code defaultValue} parameter value.
   */
  public String retrieveArgument(String[] arguments, String name, String defaultValue) {

    if (Objects.isNull(name)) {
      throw new ArgumentUtilRuntimeException(
          ArgumentUtilMessageTemplates.PARAMETER_NAME_CANNOT_BE_NULL);
    }

    if (Objects.isNull(arguments)) {
      return defaultValue;
    }

    List<String> argumentsList = Arrays.asList(arguments);

    int index = argumentsList.indexOf(name);

    if (index < 0) {
      return defaultValue;
    }

    if (index == argumentsList.size() - 1) {
      return arguments[index];
    }

    String nextValue = arguments[index + 1];

    if (Pattern.matches(PARAMETER_NAME_REGEX, nextValue)) {
      return arguments[index];
    }

    return arguments[index + 1];
  }

  /**
   * Checks an argument was informed on an argument array.
   * 
   * @param arguments The arguments array.
   * @param name The name of the argument to be found.
   * @return {@code true} if the argument exists, {@code false} otherwise.
   */
  public boolean checkArgument(String[] arguments, String name) {
    return (Arrays.asList(arguments).indexOf(name) >= 0);
  }

}
