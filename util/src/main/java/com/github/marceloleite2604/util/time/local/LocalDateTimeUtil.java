package com.github.marceloleite2604.util.time.local;

import com.github.marceloleite2604.util.time.TimeInterval;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * A series of handy methods which helps working with {@link LocalDateTime} objects.
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class LocalDateTimeUtil {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private static final DateTimeFormatter TIMESTAMP_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

  /**
   * Checks if specified date is between a time interval.
   * 
   * @param date Date to check.
   * @param startInclusive Start time interval (inclusive).
   * @param endExclusive End time interval (exclusive).
   * @return {@code true} if date is inside the time interval specified, {@code false} otherwise.
   */
  public boolean isBetween(LocalDateTime date, LocalDateTime startInclusive,
      LocalDateTime endExclusive) {
    return ((date.isAfter(startInclusive) || date.equals(startInclusive))
        && date.isBefore(endExclusive));
  }

  /**
   * Checks if specified date is between a time interval considering time interval at the same zone.
   * 
   * @param date Date to check.
   * @param timeInterval time interval to check.
   * @return {@code true} if date is inside the time interval specified, {@code false} otherwise.
   */
  public boolean isBetween(LocalDateTime date, TimeInterval timeInterval) {
    LocalDateTime start =
        timeInterval.getStart().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    LocalDateTime end = timeInterval.getEnd().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    return isBetween(date, start, end);
  }

  /**
   * Converts an epoch time to a {@link LocalDateTime} object using UTC zone id.
   * 
   * @param epochTime The epoch time to be converted.
   * @return A {@link LocalDateTime} object with time specified on {@code epochTime} parameter on
   *         UTC zone offset.
   */
  public LocalDateTime convertFromEpochTime(long epochTime) {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneOffset.UTC);
  }

  /**
   * Converts a {@link LocalDateTime} object to a text written according to ISO-8601 format.
   * 
   * @param localDateTime Time to convert as text.
   * @return A ISO-8601 formatted text elaborated from {@code localDateTime} parameter.
   */
  public String toStringAsISODateTime(LocalDateTime localDateTime) {
    return DATE_TIME_FORMATTER.format(localDateTime);
  }

  /**
   * Parses a ISO-8601 formatted text and convert it to a {@link LocalDateTime} object.
   * 
   * @param text Text to be parsed.
   * @return A {@link LocalDateTime} containing the date and time specified on text.
   */
  public LocalDateTime parseFromISOFormat(String text) {
    return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
  }

  /**
   * Converts a {@link LocalDateTime} to an epoch time on UTC zone offset.
   * 
   * @param localDateTime Time to be converted
   * @return The epoch time retrieved from the {@code localDateTime} parameter.
   */
  public long convertAsEpochTimeOnUTCZoneOffset(LocalDateTime localDateTime) {
    return localDateTime.toEpochSecond(ZoneOffset.UTC);
  }

  /**
   * Converts a {@link LocalDateTime} to an epoch time on system's default zone offset.
   * 
   * @param localDateTime Time to be converted
   * @return The epoch time retrieved from the {@code localDateTime} parameter.
   */
  public long convertAsEpochTimeOnSystemDefaultZonedOffset(LocalDateTime localDateTime) {
    ZoneId systemZone = ZoneId.systemDefault();
    ZoneOffset systemDefaultZoneOffset = systemZone.getRules().getOffset(Instant.now());
    return localDateTime.toEpochSecond(systemDefaultZoneOffset);
  }

  /**
   * Converts a {@link LocalDateTime} object to a preformatted timestamp text.
   * 
   * @param localDateTime Time to convert as timestamp text.
   * @return The preformatted timestamp text elaborated from {@code localDateTime} parameter.
   */
  public String toStringAsTimestamp(LocalDateTime localDateTime) {
    return TIMESTAMP_FORMATTER.format(localDateTime);
  }

  /**
   * Converts a preformatted timestamp text to a {@link LocalDateTime} object.
   * 
   * @param text Text to be converted.
   * @return A {@link LocalDateTime} object with the time specified on {@code text} parameter.
   */
  public LocalDateTime parseFromTimestamp(String text) {
    return LocalDateTime.parse(text, TIMESTAMP_FORMATTER);
  }

}
