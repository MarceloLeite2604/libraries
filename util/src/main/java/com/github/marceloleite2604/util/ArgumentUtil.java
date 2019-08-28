package com.github.marceloleite2604.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ArgumentUtil {

	public Optional<String> retrieveArgument(String[] args, String parameter) {
		String argument = retrieveArgument(args, parameter, null);
		if (argument == null) {
			return Optional.empty();
		} else {
			return Optional.of(argument);
		}
	}

	public String retrieveArgument(String[] args, String parameter, String defaultValue) {

		String content = null;

		List<String> arguments = Arrays.asList(args);

		int index = arguments.indexOf(parameter);
		if (index >= 0) {
			if (index == arguments.size()) {
				return defaultValue;
			}
			content = arguments.get(index + 1);

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

	public boolean checkArgument(String[] args, String parameter) {
		return (Arrays.asList(args)
				.indexOf(parameter) >= 0);
	}
}
