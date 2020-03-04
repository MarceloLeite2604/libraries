package com.figtreelake.util.time;

import static org.assertj.core.api.Assertions.assertThat;

import com.figtreelake.util.time.TimeInterval;
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
  public void testToString() throws Exception {
    // Arrange
    LocalDateTime start = LocalDateTime.of(2020, 3, 2, 12, 4, 14, 6295712);
    LocalDateTime end = LocalDateTime.of(2020, 3, 2, 18, 7, 51, 45792468);

    TimeInterval timeInterval = new TimeInterval(start, end);

    String expectedString =
        "2020-03-02T12:04:14.006295712 to 2020-03-02T18:07:51.045792468 (PT6H3M37.039496756S)";

    // Act
    String actualString = timeInterval.toString();

    // Assert
    assertThat(actualString).isEqualTo(expectedString);
  }

}
