package com.github.marceloleite2604.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ArgumentUtil {

	/**
	 * Retrieves the value of an optional argument.
	 * 
	 * @param arguments
	 *            The arguments array.
	 * @param name
	 *            Name of the argument to be retrieved.
	 * @return An {@link Optional} containing the argument value. If no argument was
	 *         found, returns an empty {@link Optional}.
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
	 * Retrieves the value of an optional argument. If no value was found, returns
	 * the value informed on {@code defaultValue} parameter.
	 * 
	 * @param arguments
	 *            The arguments array.
	 * @param name
	 *            Name of the argument to be retrieved.
	 * @param defaultValue
	 *            Default value to be returned if the parameter is not found.
	 * @return If the argument was found, the method returns its value. Otherwise,
	 *         it will return the {@code defaultValue} parameter value.
	 */
	public String retrieveArgument(String[] arguments, String name, String defaultValue) {

		String content = null;

		List<String> argumentsList = Arrays.asList(arguments);

		int index = argumentsList.indexOf(name);
		if (index >= 0) {
			if (index == argumentsList.size()) {
				return defaultValue;
			}
			content = argumentsList.get(index + 1);

			if (Pattern.matches("-.", content)) {
				return defaultValue;
			}
		}

		if (content != null) {
			return content;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Checks an argument was informed on an argument array.
	 * 
	 * @param arguments
	 *            The arguments array.
	 * @param name
	 *            The name of the argument to be found.
	 * @return {@code true} if the argument exists, {@code false} otherwise.
	 */
	public boolean checkArgument(String[] arguments, String name) {
		return (Arrays.asList(arguments)
				.indexOf(name) >= 0);
	}
}
