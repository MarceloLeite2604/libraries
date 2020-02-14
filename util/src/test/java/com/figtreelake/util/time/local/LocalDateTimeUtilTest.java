
package com.figtreelake.util.time.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.figtreelake.util.time.TimeInterval;
import com.figtreelake.util.time.local.LocalDateTimeUtil;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest({ZoneId.class, LocalDateTimeUtil.class})
public class LocalDateTimeUtilTest {

  private static final String CUSTOM_USER_TIMEZONE_PROPERTY = "Etc/GMT+9";

  private static final ZoneId CUSTOM_USER_TIMEZONE = ZoneId.of(CUSTOM_USER_TIMEZONE_PROPERTY);

  private LocalDateTimeUtil localDateTimeUtil;

  @Rule
  public PowerMockRule powerMockRule = new PowerMockRule();

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
  public void testToStringAsIso8601() throws Exception {
    // Arrange
    String expectedFormattedLocalDateTime = "2018-09-26T15:52:59";
    LocalDateTime localDateTime = LocalDateTime.of(2018, 9, 26, 15, 52, 59);

    // Act
    String actualFormattedLocalDateTime = localDateTimeUtil.toStringAsIso8601(localDateTime);

    // Assert
    assertEquals(expectedFormattedLocalDateTime, actualFormattedLocalDateTime);
  }

  @Test
  public void testParseFromIso8601() throws Exception {
    // Arrange
    String textDate = "2018-09-26T15:52:59";
    LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, 9, 26, 15, 52, 59);

    // Act
    LocalDateTime actualLocalDateTime = localDateTimeUtil.parseFromIso8601(textDate);

    // Assert
    assertEquals(expectedLocalDateTime, actualLocalDateTime);
  }

  @Test
  public void testConvertAsEpochTimeOnUtcZoneOffset() throws Exception {
    // Arrange
    long expectedEpochTime = 1537977857L;
    LocalDateTime localDateTime = LocalDateTime.of(2018, 9, 26, 16, 4, 17);

    // Act
    long actualEpochTime = localDateTimeUtil.convertAsEpochTimeOnUtcZoneOffset(localDateTime);

    // Assert
    assertEquals(expectedEpochTime, actualEpochTime);
  }

  @Test
  public void testConvertAsEpochTimeOnSystemDefaultZonedOffset() throws Exception {
    // Arrange
    long expectedEpochTime = 1581475026L;
    LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 11, 17, 37, 6);
    
    PowerMockito.mockStatic(ZoneId.class);

    PowerMockito.when(ZoneId.systemDefault()).thenReturn(CUSTOM_USER_TIMEZONE);

    // Act
    long actualEpochTime =
        localDateTimeUtil.convertAsEpochTimeOnSystemDefaultZonedOffset(localDateTime);

    // Assert
    assertEquals(expectedEpochTime, actualEpochTime);
  }
}
