package com.github.marceloleite2604.util.time.zoned;

import com.github.marceloleite2604.util.time.TimeInterval;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

  /**
   * Returns current time on UTC.
   * 
   * @return Current time on UTC.
   */
  public ZonedDateTime nowUTCOffset() {
    return ZonedDateTime.now(ZoneOffset.UTC);
  }

  /**
   * Returns current time on system's default time zone.
   * 
   * @return Current time on system's default time zone.
   */
  public ZonedDateTime nowSystemDefaultZoneId() {
    return ZonedDateTime.now(ZoneId.systemDefault());
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
    return isBetween(date, timeInterval.getStart().atZone(date.getZone()),
        timeInterval.getEnd().atZone(date.getZone()));
  }

  /**
   * Converts an epoch time to a {@link ZonedDateTime} object using UTC offset.
   * 
   * @param epochTime The epoch time to be converted.
   * @return A {@link ZonedDateTime} object with time specified on {@code epochTime} parameter on
   *         UTC zone offset.
   */
  public ZonedDateTime convertFromEpochTimeToUTFOffset(long epochTime) {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneOffset.UTC);
  }

  /**
   * Converts an epoch time to a {@link ZonedDateTime} object using system default time zone.
   * 
   * @param epochTime The epoch time to be converted.
   * @return A {@link ZonedDateTime} object with time specified on {@code epochTime} parameter on
   *         system default time zone.
   */
  public ZonedDateTime convertFromEpochTimeToSystemDefaultZoneId(long epochTime) {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochTime), ZoneId.systemDefault());
  }

  /**
   * Converts a {@link ZonedDateTime} object to a text written according to ISO-8601 extended offset
   * date-time format.
   * 
   * @param zonedDateTime Time to convert as text.
   * @return A ISO-8601 formatted text elaborated from {@code zonedDateTime} parameter.
   */
  public String toStringAsISOOffsetDateTime(ZonedDateTime zonedDateTime) {
    return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime);
  }

  /**
   * Parses a preformatted text and convert it to a {@link ZonedDateTime} object.
   * 
   * @param text Text to be parsed.
   * @return A {@link ZonedDateTime} containing the date specified on the predefined text.
   */
  public ZonedDateTime parseFromISOOffsetFormat(String text) {
    return ZonedDateTime.parse(text, DateTimeFormatter.ISO_ZONED_DATE_TIME);
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
}
