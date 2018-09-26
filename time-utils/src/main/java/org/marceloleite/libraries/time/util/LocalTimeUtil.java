package org.marceloleite.libraries.time.util;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class LocalTimeUtil {

	public static final int SECONDS_IN_A_DAY = 86400;

	public static final String DATE_FORMAT = "HH:mm:ss";

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
	private LocalTimeUtil() {
	}

	public static boolean isBetween(LocalTime timeToCheck, LocalTime startTime, Duration duration) {

		int timeToCheckInSeconds = timeToCheck.toSecondOfDay();
		int startTimeInSeconds = startTime.toSecondOfDay();
		int endTimeInSeconds = startTime.plus(duration)
				.toSecondOfDay();

		if (endTimeInSeconds < startTimeInSeconds) {
			endTimeInSeconds += SECONDS_IN_A_DAY;
		}

		return (timeToCheckInSeconds >= startTimeInSeconds && timeToCheckInSeconds < endTimeInSeconds);
	}

	public static LocalTime parse(String value) {
		return LocalTime.parse(value, DATE_TIME_FORMATTER);
	}

	public static String format(LocalTime localTime) {
		return DATE_TIME_FORMATTER.format(localTime);
	}

}
