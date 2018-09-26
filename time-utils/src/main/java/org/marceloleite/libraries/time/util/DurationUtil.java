package org.marceloleite.libraries.time.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class DurationUtil {

	private static final String NANO_UNIT = "nanosecond";

	private static final String SECOND_UNIT = "second";

	private static final String MINUTE_UNIT = "minute";

	private static final String DAY_UNIT = "day";

	private static final String HOUR_UNIT = "hour";

	private static final long NANOS_IN_A_SECOND = 1000000000l;

	private static final long SECONDS_IN_A_MINUTE = 60l;

	private static final long MINUTES_IN_AN_HOUR = 60l;

	private static final long HOUR_IN_A_DAY = 24l;

	private DurationUtil() {
	}

	public static String formatAsSpelledNumber(Duration duration) {
		long total;
		List<String> strings = new ArrayList<>();

		try {
			total = duration.toNanos();
			total = calculateValue(strings, total, NANOS_IN_A_SECOND, NANO_UNIT);
			calculateValue(strings, total, SECONDS_IN_A_MINUTE, SECOND_UNIT);
		} catch (ArithmeticException exception) {
			throw new RuntimeException("Error while spelling number.", exception);
		}

		total = duration.toMinutes();
		total = calculateValue(strings, total, MINUTES_IN_AN_HOUR, MINUTE_UNIT);
		total = calculateValue(strings, total, HOUR_IN_A_DAY, HOUR_UNIT);
		elaborateUnitText(strings, total, DAY_UNIT);

		return elaborateText(strings);

	}

	private static long calculateValue(List<String> strings, long total, long division, String unit) {
		long remainder = total % (division);

		elaborateUnitText(strings, remainder, unit);

		return (total - remainder) / division;
	}

	private static void elaborateUnitText(List<String> strings, long value, String unit) {
		if (value > 0) {
			strings.add(value + " " + unit + (value > 1 ? "s" : ""));
		}
	}

	private static String elaborateText(List<String> strings) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int counter = strings.size() - 1; counter >= 0; counter--) {
			String string = strings.get(counter);
			if (stringBuilder.length() > 0) {
				stringBuilder.append((counter > 0 ? ", " : " and "));
			}
			stringBuilder.append(string);
		}
		return stringBuilder.toString();
	}

	public static Double formatAsSeconds(Duration duration) {
		double seconds = 0.0;
		seconds += (double) duration.getNano() / (double) NANOS_IN_A_SECOND;
		seconds += (double) duration.getSeconds();

		return seconds;
	}

	public static Duration parseFromSeconds(double seconds) {
		long floorSeconds = (long) seconds;
		long nanoseconds = (long) ((seconds - (double) floorSeconds) * NANOS_IN_A_SECOND);
		return Duration.ofSeconds(floorSeconds)
				.plusNanos(nanoseconds);
	}
}
