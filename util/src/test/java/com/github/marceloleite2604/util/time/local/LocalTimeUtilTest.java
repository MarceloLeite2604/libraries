package com.github.marceloleite2604.util.time.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
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
  public void testIsBetweenInsideLimits() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(13, 17);
    LocalTime startTime = LocalTime.of(12, 0);
    Duration duration = Duration.ofHours(4);

    // Act and assert
    assertTrue(localTimeUtil.isBetween(timeToCheck, startTime, duration));
  }

  @Test
  public void testIsBetweenOutsideLimits() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(0, 2);
    LocalTime startTime = LocalTime.of(0, 0);
    Duration duration = Duration.ofMinutes(1);

    // Act and assert
    assertFalse(localTimeUtil.isBetween(timeToCheck, startTime, duration));
  }

  @Test
  public void testIsBetweenUpperLimitExclusive() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(16, 0);
    LocalTime startTime = LocalTime.of(12, 0);
    Duration duration = Duration.ofHours(4);

    // Act and assert
    assertFalse(localTimeUtil.isBetween(timeToCheck, startTime, duration));
  }

  @Test
  public void testIsBetweenLowerLimitInclusive() throws Exception {
    // Arrange
    LocalTime timeToCheck = LocalTime.of(12, 0);
    LocalTime startTime = LocalTime.of(12, 0);
    Duration duration = Duration.ofHours(1);

    // Act and assert
    assertTrue(localTimeUtil.isBetween(timeToCheck, startTime, duration));
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
