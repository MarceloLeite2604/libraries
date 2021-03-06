package com.figtreelake.util.time;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Contains a time interval which contains its start time, end time and duration.
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class TimeInterval implements Comparable<TimeInterval> {

  private LocalDateTime start;

  private LocalDateTime end;

  private Duration duration;

  /**
   * Create a time interval between {@code start} time and {@code end} time.
   * 
   * @param start time interval start time.
   * @param end time interval end time.
   */
  public TimeInterval(LocalDateTime start, LocalDateTime end) {
    super();
    this.start = start;
    this.end = end;
    this.duration = Duration.between(start, end);
  }

  /**
   * Create a time interval starting at {@code start} and with a duration of {@code duration}.
   * 
   * @param start time interval start time.
   * @param duration time interval duration.
   */
  public TimeInterval(LocalDateTime start, Duration duration) {
    super();
    this.start = start;
    this.duration = duration;
    this.end = start.plus(duration);
  }

  /**
   * Create a time interval with a duration of {@code duration} and ending at {@code end}.
   * 
   * @param duration time interval duration.
   * @param end time interval end time.
   */
  public TimeInterval(Duration duration, LocalDateTime end) {
    super();
    this.duration = duration;
    this.end = end;
    this.start = end.minus(duration);
  }

  public LocalDateTime getStart() {
    return start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public Duration getDuration() {
    return duration;
  }

  @Override
  public String toString() {
    return start + " to " + end + "(" + duration + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((end == null) ? 0 : end.hashCode());
    result = prime * result + ((start == null) ? 0 : start.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    TimeInterval other = (TimeInterval) obj;
    if (end == null) {
      if (other.end != null) {
        return false;
      }
    } else if (!end.equals(other.end)) {
      return false;
    }
    if (start == null) {
      if (other.start != null) {
        return false;
      }
    } else if (!start.equals(other.start)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(TimeInterval other) {
    if (start.isBefore(other.getStart())) {
      return -1;
    } else if (start.isAfter(other.getStart())) {
      return 1;
    } else {
      return 0;
    }
  }

}
