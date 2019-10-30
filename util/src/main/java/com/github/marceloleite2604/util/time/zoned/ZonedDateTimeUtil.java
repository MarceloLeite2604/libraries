package com.github.marceloleite2604.util.time.zoned;

import com.github.marceloleite2604.util.time.TimeInterval;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A series of handy methods which helps working with {@link ZonedDateTime} objects.
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class ZonedDateTimeUtil {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  private static final DateTimeFormatter TIMESTAMP_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  /**
   * Default zone id used by this class.
   */
  public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

  /**
   * Returns current time on predefined {@link ZoneId}.
   * 
   * @return Current time on predefined {@link ZoneId}.
   */
  public ZonedDateTime now() {
    return ZonedDateTime.now(DEFAULT_ZONE_ID);
  }

  /**
   * Checks if specified date is between a time interval.
   * 
   * @param date Date to check.
   * @param startInclusive Start time interval (inclusive).
   * @param endExclusive End time interval (exclusive).
   * @return {@code true} if date is inside the time interval specified, {@code false} otherwise.
   */
  public boolean isBetween(ZonedDateTime date, ZonedDateTime startInclusive,
      ZonedDateTime endExclusive) {
    return ((date.isAfter(startInclusive) || date.equals(startInclusive))
        && date.isBefore(endExclusive));
  }

  /**
   * Checks if specified date is between a time interval.
   * 
   * @param date Date to check.
   * @param timeInterval time interval to check.
   * @return {@code true} if date is inside the time interval specified, {@code false} otherwise.
   */
  public boolean isBetween(ZonedDateTime date, TimeInterval timeInterval) {
    return isBetween(date, timeInterval.getStart(), timeInterval.getEnd());
  }

  /**
   * Converts an epoch time to a {@link ZonedDateTime} object.
   * 
   * @param epochTime The epoch time to be converted.
   * @return A {@link ZonedDateTime} object with time specified on {@code epochTime} parameter.
   */
  public ZonedDateTime convertFromEpochTime(long epochTime) {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochTime), DEFAULT_ZONE_ID);
  }

  /**
   * Converts a {@link ZonedDateTime} object to a preformatted text.
   * 
   * @param zonedDateTime Time to convert as text.
   * @return The preformatted text elaborated from {@code zonedDateTime} parameter.
   */
  public String toString(ZonedDateTime zonedDateTime) {
    return DATE_TIME_FORMATTER.format(zonedDateTime);
  }

  /**
   * Parses a preformatted text and convert it to a {@link ZonedDateTime} object.
   * 
   * @param text Text to be parsed.
   * @return A {@link ZonedDateTime} containing the date specified on the predefined text.
   */
  public ZonedDateTime parse(String text) {
    return ZonedDateTime.parse(text, DATE_TIME_FORMATTER);
  }

  /**
   * Converts a {@link ZonedDateTime} to an epoch time.
   * 
   * @param zonedDateTime Time to be converted
   * @return The epoch time retrieved from the {@code zonedDateTime} parameter.
   */
  public long convertAsEpochTime(ZonedDateTime zonedDateTime) {
    return zonedDateTime.toInstant().getEpochSecond();
  }

  /**
   * Converts a {@link ZonedDateTime} object to another {@link ZonedDateTime} object, but using
   * system default zone id.
   * 
   * @param zonedDateTime Time to be converted.
   * @return A {@link ZonedDateTime} object with time informed on {@code zonedDateTime} parameter
   *         converted to system default zone id.
   */
  public ZonedDateTime toSystemDefaultZoneId(ZonedDateTime zonedDateTime) {
    return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
  }

  /**
   * Converts a {@link ZonedDateTime} object to another {@link ZonedDateTime} object, but using the
   * zone id specified on {@link ZonedDateTimeUtil#DEFAULT_ZONE_ID} constant.
   * 
   * @param zonedDateTime Time to be converted.
   * @return A {@link ZonedDateTime} object with time informed on {@code zonedDateTime} parameter
   *         converted to this class default zone id.
   */
  public ZonedDateTime toDefaultZoneId(ZonedDateTime zonedDateTime) {
    return zonedDateTime.withZoneSameInstant(DEFAULT_ZONE_ID);
  }

  /**
   * Converts a {@link ZonedDateTime} object to a preformatted timestamp text.
   * 
   * @param zonedDateTime Time to convert as timestamp text.
   * @return The preformatted timestamp text elaborated from {@code zonedDateTime} parameter.
   */
  public String toStringAsTimestamp(ZonedDateTime zonedDateTime) {
    return TIMESTAMP_FORMATTER.format(zonedDateTime);
  }

  /**
   * Converts a preformatted timestamp text to a {@link ZonedDateTime} object.
   * 
   * @param text Text to be converted.
   * @return A {@link ZonedDateTime} object with the time specified on {@code text} parameter.
   */
  public ZonedDateTime convertFromWrittenTimestamp(String text) {
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(text, TIMESTAMP_FORMATTER);
    if (zonedDateTime.getZone().equals(ZoneId.of("Z"))) {
      return toDefaultZoneId(zonedDateTime);
    }
    return zonedDateTime;
  }

}
