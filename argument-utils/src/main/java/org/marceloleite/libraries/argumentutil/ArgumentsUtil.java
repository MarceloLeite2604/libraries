package org.marceloleite.libraries.argumentutil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ArgumentsUtil {

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

		return Optional.of(content);
	}

	public boolean checkArgument(String[] args, String parameter) {
		return (Arrays.asList(args)
				.indexOf(parameter) >= 0);
	}
}
