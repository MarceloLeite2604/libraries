
package com.github.marceloleite2604.util.time.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.marceloleite2604.util.time.TimeInterval;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class LocalDateTimeUtilTest {

  private LocalDateTimeUtil localDateTimeUtil;

  @Before
  public void setUp() {
    this.localDateTimeUtil = new LocalDateTimeUtil();
  }

  @Test
  public void testIsBetweenTrueWhenBetweenLimits() throws Exception {
    // Arrange
    LocalDateTime lowerLimit = LocalDateTime.of(2020, 2, 11, 6, 54);
    LocalDateTime upperLimit = LocalDateTime.of(2020, 2, 11, 7, 12);
    LocalDateTime zonedDateTime = LocalDateTime.of(2020, 2, 11, 7, 5);

    // Act and assert
    assertTrue(localDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenFalseWhenOutsideLimits() throws Exception {
    // Arrange
    LocalDateTime lowerLimit = LocalDateTime.of(2020, 2, 11, 6, 54);
    LocalDateTime upperLimit = LocalDateTime.of(2020, 2, 11, 7, 12);
    LocalDateTime zonedDateTime = LocalDateTime.of(2020, 2, 10, 7, 5);

    // Act and assert
    assertFalse(localDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenTrueEqualLowerLimit() throws Exception {
    // Arrange
    LocalDateTime lowerLimit = LocalDateTime.of(2020, 2, 11, 6, 54);
    LocalDateTime upperLimit = LocalDateTime.of(2020, 2, 11, 7, 12);
    LocalDateTime zonedDateTime = lowerLimit;

    // Act and assert
    assertTrue(localDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenFalseEqualUpperLimit() throws Exception {
    // Arrange
    LocalDateTime lowerLimit = LocalDateTime.of(2020, 2, 11, 6, 54);
    LocalDateTime upperLimit = LocalDateTime.of(2020, 2, 11, 7, 12);
    LocalDateTime zonedDateTime = upperLimit;

    // Act and assert
    assertFalse(localDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenTimeIntervalTrueWhenInsideInterval() throws Exception {
    // Arrange
    LocalDateTime start = LocalDateTime.of(2020, 2, 12, 10, 15);
    LocalDateTime end = LocalDateTime.of(2020, 2, 12, 13, 40);
    TimeInterval timeInterval = new TimeInterval(start, end);
    LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 12, 12, 00);

    // Act and assert
    assertTrue(localDateTimeUtil.isBetween(localDateTime, timeInterval));
  }

  @Test
  public void testIsBetweenTimeIntervalFalseWhenOutsideInterval() throws Exception {
    // Arrange
    LocalDateTime start = LocalDateTime.of(2020, 2, 12, 10, 15);
    LocalDateTime end = LocalDateTime.of(2020, 2, 12, 13, 40);
    TimeInterval timeInterval = new TimeInterval(start, end);
    LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 12, 7, 14);

    // Act and assert
    assertFalse(localDateTimeUtil.isBetween(localDateTime, timeInterval));
  }

  @Test
  public void testConvertFromEpochTime() throws Exception {
    // Arrange
    long epochTime = 1537976456L;
    LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, 9, 26, 15, 40, 56);

    // Act
    LocalDateTime actualLocalDateTime = localDateTimeUtil.convertFromEpochTime(epochTime);

    // Assert
    assertEquals(expectedLocalDateTime, actualLocalDateTime);
  }

  @Test
  public void testToStringAsISO8601() throws Exception {
    // Arrange
    String expectedFormattedLocalDateTime = "2018-09-26T15:52:59";
    LocalDateTime localDateTime = LocalDateTime.of(2018, 9, 26, 15, 52, 59);

    // Act
    String actualFormattedLocalDateTime = localDateTimeUtil.toStringAsISO8601(localDateTime);

    // Assert
    assertEquals(expectedFormattedLocalDateTime, actualFormattedLocalDateTime);
  }

  @Test
  public void testParseFromISO8601() throws Exception {
    // Arrange
    String textDate = "2018-09-26T15:52:59";
    LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, 9, 26, 15, 52, 59);

    // Act
    LocalDateTime actualLocalDateTime = localDateTimeUtil.parseFromISO8601(textDate);

    // Assert
    assertEquals(expectedLocalDateTime, actualLocalDateTime);
  }

  @Test
  public void testConvertAsEpochTimeOnUTCZoneOffset() throws Exception {
    // Arrange
    long expectedEpochTime = 1537977857L;
    LocalDateTime localDateTime = LocalDateTime.of(2018, 9, 26, 16, 4, 17);

    // Act
    long actualEpochTime = localDateTimeUtil.convertAsEpochTimeOnUTCZoneOffset(localDateTime);

    // Assert
    assertEquals(expectedEpochTime, actualEpochTime);
  }

  @Test
  public void testConvertAsEpochTimeOnSystemDefaultZonedOffset() throws Exception {
 // Arrange
    long expectedEpochTime = 1581453426L;
    LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 11, 17, 37, 6);

    // Act
    long actualEpochTime = localDateTimeUtil.convertAsEpochTimeOnSystemDefaultZonedOffset(localDateTime);

    // Assert
    assertEquals(expectedEpochTime, actualEpochTime);
  }
}
