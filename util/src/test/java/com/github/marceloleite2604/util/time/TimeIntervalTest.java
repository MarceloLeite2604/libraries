package com.github.marceloleite2604.util.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.Test;

public class TimeIntervalTest {

  @Test
  public void testTimeIntervalLocalDateTimeLocalDateTime() throws Exception {
    // Arrange
    LocalDateTime start = LocalDateTime.of(2019, 8, 1, 0, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(2019, 8, 2, 12, 30, 15, 200000);
    Duration expectedDuration =
        Duration.ofDays(1).plusHours(12).plusMinutes(30).plusSeconds(15).plusNanos(200000);

    TimeInterval timeInterval = new TimeInterval(start, end);

    // Act
    Duration actualDuration = timeInterval.getDuration();

    // Assert
    assertThat(actualDuration).isEqualTo(expectedDuration);
  }

  @Test
  public void testTimeIntervalLocalDateTimeDuration() throws Exception {
    // Arrange
    LocalDateTime start = LocalDateTime.of(2019, 8, 1, 0, 0, 0, 0);
    Duration duration =
        Duration.ofDays(1).plusHours(6).plusMinutes(20).plusSeconds(40).plusNanos(40000);

    LocalDateTime expectedEnd = LocalDateTime.of(2019, 8, 2, 6, 20, 40, 40000);

    TimeInterval timeInterval = new TimeInterval(start, duration);

    // Act
    LocalDateTime actualEnd = timeInterval.getEnd();

    // Assert
    assertThat(actualEnd).isEqualTo(expectedEnd);
  }

  @Test
  public void testTimeIntervalDurationLocalDateTime() throws Exception {
    // Arrange

    Duration duration =
        Duration.ofDays(1).plusHours(6).plusMinutes(20).plusSeconds(40).plusNanos(40000);

    LocalDateTime end = LocalDateTime.of(2019, 5, 7, 6, 20, 40, 40000);

    LocalDateTime expectedStart = LocalDateTime.of(2019, 5, 6, 0, 0, 0, 0);

    TimeInterval timeInterval = new TimeInterval(duration, end);

    // Act
    LocalDateTime actualStart = timeInterval.getStart();

    // Assert
    assertThat(actualStart).isEqualTo(expectedStart);
  }

  @Test
  public void testCompareToShouldReturnNegativeValueWhenFirstTimeStartsBeforeSecondTime()
      throws Exception {
    // Arrange
    LocalDateTime firstTimeIntervalStart = LocalDateTime.of(2019, 9, 1, 12, 40, 0, 0);
    LocalDateTime firstTimeIntervalEnd = LocalDateTime.of(2019, 9, 2, 3, 0, 0, 0);

    LocalDateTime secondTimeIntervalStart = LocalDateTime.of(2019, 9, 1, 12, 40, 0, 100);

    Duration secondTimeIntervalDuration =
        Duration.ofDays(2).plusHours(2).plusMinutes(14).plusSeconds(30);

    TimeInterval firstTimeInterval = new TimeInterval(firstTimeIntervalStart, firstTimeIntervalEnd);

    TimeInterval secondTimeInterval =
        new TimeInterval(secondTimeIntervalStart, secondTimeIntervalDuration);

    // Act
    int actualResult = firstTimeInterval.compareTo(secondTimeInterval);

    // Assert
    assertThat(actualResult).isLessThan(0);
  }

  @Test
  public void testCompareToShouldReturnPositiveValueWhenFirstTimeStartsAfterSecondTime()
      throws Exception {
    // Arrange
    LocalDateTime firstTimeIntervalStart = LocalDateTime.of(2019, 9, 1, 12, 40, 0, 0);
    LocalDateTime firstTimeIntervalEnd = LocalDateTime.of(2019, 9, 2, 3, 0, 0, 0);

    LocalDateTime secondTimeIntervalStart = LocalDateTime.of(2019, 9, 1, 12, 39, 59, 999999);

    Duration secondTimeIntervalDuration =
        Duration.ofDays(2).plusHours(2).plusMinutes(14).plusSeconds(30);

    TimeInterval firstTimeInterval = new TimeInterval(firstTimeIntervalStart, firstTimeIntervalEnd);

    TimeInterval secondTimeInterval =
        new TimeInterval(secondTimeIntervalStart, secondTimeIntervalDuration);

    // Act
    int actualResult = firstTimeInterval.compareTo(secondTimeInterval);

    // Assert
    assertThat(actualResult).isGreaterThan(0);
  }

  @Test
  public void testCompareToShouldReturnZeroWhenFirstTimeStartsAtSameTimeThanSecondTime()
      throws Exception {
    // Arrange
    LocalDateTime timeIntervalStart = LocalDateTime.of(2019, 9, 1, 12, 40, 0, 0);
    LocalDateTime firstTimeIntervalEnd = LocalDateTime.of(2019, 9, 2, 3, 0, 0, 0);

    Duration secondTimeIntervalDuration =
        Duration.ofDays(2).plusHours(2).plusMinutes(14).plusSeconds(30);

    TimeInterval firstTimeInterval = new TimeInterval(timeIntervalStart, firstTimeIntervalEnd);

    TimeInterval secondTimeInterval =
        new TimeInterval(timeIntervalStart, secondTimeIntervalDuration);

    // Act
    int actualResult = firstTimeInterval.compareTo(secondTimeInterval);

    // Assert
    assertThat(actualResult).isEqualTo(0);
  }

}
