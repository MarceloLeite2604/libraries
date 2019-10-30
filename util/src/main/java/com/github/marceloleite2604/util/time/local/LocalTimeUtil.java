package com.github.marceloleite2604.util.time.local;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A series of handy methods which helps working with {@link LocalTime} objects.
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class LocalTimeUtil {

  public static final int SECONDS_IN_A_DAY = 86400;

  public static final String DATE_FORMAT = "HH:mm:ss";

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern(DATE_FORMAT);

  /**
   * Checks if time informed is between a start time and a duration.
   * 
   * @param time Time to be checked.
   * @param startTime Start time interval.
   * @param duration Interval duration.
   * @return {@code true} if time is on specified time interval, {@code false} otherwise.
   */
  public boolean isBetween(LocalTime time, LocalTime startTime, Duration duration) {

    int timeToCheckInSeconds = time.toSecondOfDay();
    int startTimeInSeconds = startTime.toSecondOfDay();
    int endTimeInSeconds = startTime.plus(duration).toSecondOfDay();

    if (endTimeInSeconds < startTimeInSeconds) {
      endTimeInSeconds += SECONDS_IN_A_DAY;
    }

    return (timeToCheckInSeconds >= startTimeInSeconds && timeToCheckInSeconds < endTimeInSeconds);
  }

  /**
   * Converts a text to a {@link LocalTime} object.
   * 
   * @param text Text to be parsed.
   * @return A {@link LocalTime} object with the value parsed from {@code parameter}.
   */
  public LocalTime parse(String text) {
    return LocalTime.parse(text, DATE_TIME_FORMATTER);
  }

  /**
   * Writes a {@link LocalTime} object as a preformatted text.
   * 
   * @param localTime time to be written.
   * @return A preformatted text with the content of the {@code localTime} parameter.
   */
  public String toString(LocalTime localTime) {
    return DATE_TIME_FORMATTER.format(localTime);
  }

}
