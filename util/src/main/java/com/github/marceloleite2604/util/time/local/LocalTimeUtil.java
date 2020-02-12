package com.github.marceloleite2604.util.time.local;

import com.github.marceloleite2604.util.time.TimeInterval;
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

  /**
   * Checks if time informed is between a start time and a duration.
   * 
   * @param time Time to be checked.
   * @param start Start time interval.
   * @param duration Interval duration.
   * @return {@code true} if time is on specified time interval, {@code false} otherwise.
   */
  public boolean isBetween(LocalTime time, LocalTime start, Duration duration) {
    LocalTime end = start.plusSeconds(duration.getSeconds());
    return isBetween(time, start, end);
  }

  /**
   * Check if time is between an interval.
   * 
   * @param time Time to check
   * @param start Start of time interval (inclusive).
   * @param end End of time interval (exclusive)
   * @return True if time is between the interval informed. False otherwise.
   */
  public boolean isBetween(LocalTime time, LocalTime start, LocalTime end) {

    int timeToCheckInSeconds = time.toSecondOfDay();
    int startTimeInSeconds = start.toSecondOfDay();
    int endTimeInSeconds = end.toSecondOfDay();

    if (endTimeInSeconds < startTimeInSeconds) {
      endTimeInSeconds += SECONDS_IN_A_DAY;
    }

    return (timeToCheckInSeconds >= startTimeInSeconds && timeToCheckInSeconds < endTimeInSeconds);
  }

  /**
   * Check if a time is between a time interval.
   * 
   * @param time Time to check
   * @param timeInterval Interval to check if {@code time} is between
   * @return True if time is between the interval informed. False otherwise.
   */
  public boolean isBetween(LocalTime time, TimeInterval timeInterval) {
    LocalTime start = timeInterval.getStart().toLocalTime();
    LocalTime end = timeInterval.getEnd().toLocalTime();
    return isBetween(time, start, end);
  }

  /**
   * Parses a ISO-8601 text date to a {@link LocalTime} object.
   * 
   * @param text ISO-8601 text date to be parsed.
   * @return A {@link LocalTime} object with the value parsed from {@code text}.
   */
  public LocalTime parseFromIso8601Format(String text) {
    return LocalTime.parse(text, DateTimeFormatter.ISO_TIME);
  }
}
