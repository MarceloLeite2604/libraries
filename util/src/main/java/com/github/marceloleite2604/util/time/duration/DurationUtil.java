package com.github.marceloleite2604.util.time.duration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.marceloleite2604.util.time.TimeUtilRuntimeException;

/**
 * A series of handy methods which helps working with {@link Duration} objects.
 * 
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target=
 *      "_top">GitHub project</a>
 * 
 * @author MarceloLeite2604
 *
 */
public class DurationUtil {

	private static final String NANO_UNIT = "nanosecond";

	private static final String SECOND_UNIT = "second";

	private static final String MINUTE_UNIT = "minute";

	private static final String DAY_UNIT = "day";

	private static final String HOUR_UNIT = "hour";

	private static final long NANOS_IN_A_SECOND = 1000000000l;

	private static final long SECONDS_IN_A_MINUTE = 60l;

	private static final long MINUTES_IN_AN_HOUR = 60l;

	private static final long HOUR_IN_A_DAY = 24l;

	/**
	 * Writes a {@link Duration} as text.
	 * 
	 * @param duration
	 *            The duration to be written.
	 * @return A text informing the duration.
	 */
	public String writeAsText(Duration duration) {

		if (Objects.isNull(duration)) {
			throw new TimeUtilRuntimeException(DurationMessageTemplates.DURATION_CANNOT_BE_NULL);
		}

		long total;
		List<String> strings = new ArrayList<>();

		total = duration.toNanos();
		total = calculateValue(strings, total, NANOS_IN_A_SECOND, NANO_UNIT);
		calculateValue(strings, total, SECONDS_IN_A_MINUTE, SECOND_UNIT);

		total = duration.toMinutes();
		total = calculateValue(strings, total, MINUTES_IN_AN_HOUR, MINUTE_UNIT);
		total = calculateValue(strings, total, HOUR_IN_A_DAY, HOUR_UNIT);
		elaborateUnitText(strings, total, DAY_UNIT);

		return elaborateText(strings);

	}

	private long calculateValue(List<String> strings, long total, long division, String unit) {
		long remainder = total % (division);

		elaborateUnitText(strings, remainder, unit);

		return (total - remainder) / division;
	}

	private void elaborateUnitText(List<String> strings, long value, String unit) {
		if (value > 0) {
			strings.add(value + " " + unit + (value > 1 ? "s" : ""));
		}
	}

	private String elaborateText(List<String> strings) {
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

	/**
	 * Returns the amount of seconds in a {@link Duration}.
	 * 
	 * @param duration
	 *            The duration to retrieve the seconds.
	 * @return The amount of seconds in the {@link Duration} informed.
	 */
	public double formatAsSeconds(Duration duration) {

		if (Objects.isNull(duration)) {
			throw new TimeUtilRuntimeException(DurationMessageTemplates.DURATION_CANNOT_BE_NULL);
		}

		double seconds = 0.0;
		seconds += (double) duration.getNano() / (double) NANOS_IN_A_SECOND;
		seconds += (double) duration.getSeconds();

		return seconds;
	}

	/**
	 * Creates a {@link Duration} object with the amount of seconds informed.
	 * 
	 * @param seconds
	 *            The amount of seconds the {@link Duration} object must have.
	 * @return A {@link Duration} object with the amount of seconds informed.
	 */
	public Duration parseFromSeconds(double seconds) {
		long floorSeconds = (long) seconds;
		long nanoseconds = (long) ((seconds - (double) floorSeconds) * NANOS_IN_A_SECOND);
		return Duration.ofSeconds(floorSeconds)
				.plusNanos(nanoseconds);
	}
}
