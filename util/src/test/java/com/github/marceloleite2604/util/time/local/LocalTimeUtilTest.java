package com.github.marceloleite2604.util.time.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.marceloleite2604.util.time.TimeInterval;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class LocalTimeUtilTest {

  private LocalTimeUtil localTimeUtil;

  @Before
  public void setUp() {
    this.localTimeUtil = new LocalTimeUtil();
  }

  @Test
  public void testIsBetweenLocalTimeLocalTimeLocalTimeInsideLimits() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(13, 17);
    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(16, 0);

    // Act and assert
    assertTrue(localTimeUtil.isBetween(timeToCheck, startTime, endTime));
  }

  @Test
  public void testIsBetweenLocalTimeLocalTimeLocalTimeOutsideLimits() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(0, 2);
    LocalTime startTime = LocalTime.of(0, 0);
    LocalTime endTime = LocalTime.of(0, 1);

    // Act and assert
    assertFalse(localTimeUtil.isBetween(timeToCheck, startTime, endTime));
  }

  @Test
  public void testIsBetweenLocalTimeLocalTimeLocalTimeUpperLimitExclusive() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(16, 0);
    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(16, 0);

    // Act and assert
    assertFalse(localTimeUtil.isBetween(timeToCheck, startTime, endTime));
  }

  @Test
  public void testIsBetweenLocalTimeLocalTimeLocalTimeLowerLimitInclusive() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(12, 0);
    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(13, 0);

    // Act and assert
    assertTrue(localTimeUtil.isBetween(timeToCheck, startTime, endTime));
  }

  @Test
  public void testIsBetweenLocalTimeLocalTimeDuration() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(12, 0);
    LocalTime startTime = LocalTime.of(12, 0);
    Duration duration = Duration.ofHours(1);

    // Act and assert
    assertTrue(localTimeUtil.isBetween(timeToCheck, startTime, duration));
  }

  @Test
  public void testIsBetweenLocalTimeTimeInterval() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(8, 30);
    LocalDateTime start = LocalDateTime.of(2020, 2, 12, 8, 1, 57);
    LocalDateTime end = LocalDateTime.of(2020, 2, 12, 9, 0, 0);
    TimeInterval timeInterval = new TimeInterval(start, end);

    // Act and assert
    assertTrue(localTimeUtil.isBetween(timeToCheck, timeInterval));
  }

  @Test
  public void testParseFromIso8601Format() throws Exception {
    // Arrange
    String timeText = "17:58:00";
    LocalTime expectedLocalTime = LocalTime.of(17, 58);

    // Act
    LocalTime actualLocalTime = localTimeUtil.parseFromIso8601Format(timeText);

    // Assert
    assertEquals(expectedLocalTime, actualLocalTime);
  }
}
