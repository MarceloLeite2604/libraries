package org.marceloleite.libraries.time.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.marceloleite.libraries.time.TimeInterval;

public final class ZonedDateTimeUtil {
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
	
	private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

	private ZonedDateTimeUtil() {
	}

	public static ZonedDateTime now() {
		return ZonedDateTime.now(DEFAULT_ZONE_ID);
	}

	public static boolean isBetween(ZonedDateTime date, ZonedDateTime startInclusive, ZonedDateTime endExclusive) {
		return ((date.isAfter(startInclusive) || date.equals(startInclusive)) && date.isBefore(endExclusive));
	}

	public static boolean isBetween(ZonedDateTime date, TimeInterval timeInterval) {
		return isBetween(date, timeInterval.getStart(), timeInterval.getEnd());
	}

	public static ZonedDateTime convertFromEpochTime(Long epochTime) {
		return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochTime), DEFAULT_ZONE_ID);
	}

	public static String format(ZonedDateTime zonedDateTime) {
		return DATE_TIME_FORMATTER.format(zonedDateTime);
	}

	public static ZonedDateTime parse(String string) {
		return ZonedDateTime.parse(string, DATE_TIME_FORMATTER);
	}

	public static Long formatAsEpochTime(ZonedDateTime zonedDateTime) {
		return zonedDateTime.toInstant()
				.getEpochSecond();
	}

	public static ZonedDateTime toSystemDefaultZoneId(ZonedDateTime zonedDateTime) {
		return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
	}
	
	public static ZonedDateTime toDefaultZoneId(ZonedDateTime zonedDateTime) {
		return zonedDateTime.withZoneSameInstant(DEFAULT_ZONE_ID);
	}

	public static String formatAsTimestamp(ZonedDateTime zonedDateTime) {
		return TIMESTAMP_FORMATTER.format(zonedDateTime);
	}

	public static ZonedDateTime convertFromWrittenTimestamp(String string) {
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(string, TIMESTAMP_FORMATTER);
		if (zonedDateTime.getZone().equals(ZoneId.of("Z"))) {
			return ZonedDateTimeUtil.toDefaultZoneId(zonedDateTime);
		}
		return zonedDateTime;
	}
}
