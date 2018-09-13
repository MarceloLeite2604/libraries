package org.marceloleite.libraries.argumentsutils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ArgumentsUtils {

	public Optional<String> retrieveArgument(String[] args, String parameter) {

		String content = null;

		List<String> arguments = Arrays.asList(args);

		int index = arguments.indexOf(parameter);
		if (index >= 0) {
			if (index == arguments.size()) {
				return Optional.empty();
			}
			content = arguments.get(index + 1);

			if (Pattern.matches("-.", content)) {
				return Optional.empty();
			}
		}

		if (content != null) {
			return Optional.of(content);
		} else {
			return Optional.empty();
		}
	}

	public boolean checkArgument(String[] args, String parameter) {
		return (Arrays.asList(args)
				.indexOf(parameter) >= 0);
	}
}
