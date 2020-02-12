package com.github.marceloleite2604.util.time.zoned;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.marceloleite2604.util.time.TimeInterval;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest({ZoneId.class, ZonedDateTimeUtil.class})
public class ZonedDateTimeUtilTest {

  private static final String CUSTOM_USER_TIMEZONE_PROPERTY = "Etc/GMT-3";

  private static final ZoneId CUSTOM_USER_TIMEZONE = ZoneId.of(CUSTOM_USER_TIMEZONE_PROPERTY);

  private ZonedDateTimeUtil zonedDateTimeUtil;

  @Rule
  public PowerMockRule rule = new PowerMockRule();

  @Before
  public void setUp() {
    this.zonedDateTimeUtil = new ZonedDateTimeUtil();
  }

  @Test
    public void testNowUtcOffset() throws Exception {
  
      // Arrange
      ZoneId expectedZoneId = ZoneOffset.UTC;
      // Act
  
      ZonedDateTime actualResult = zonedDateTimeUtil.nowUtcOffset();
  
      // Assert
      assertThat(actualResult).isNotNull();
      assertThat(actualResult.getZone()).isEqualTo(expectedZoneId);
    }

  @Test
  public void testNowSystemDefaultTimeZoneId() throws Exception {

    // Arrange
    ZoneId expectedZoneId = ZoneId.systemDefault();
    // Act

    ZonedDateTime actualResult = zonedDateTimeUtil.nowSystemDefaultZoneId();

    // Assert
    assertThat(actualResult).isNotNull();
    assertThat(actualResult.getZone()).isEqualTo(expectedZoneId);
  }

  @Test
  public void testIsBetweenZonedDateTimeInsideBounds() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneOffsets.PLUS_3);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneOffsets.PLUS_3);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneOffsets.PLUS_FOUR);

    // Act and assert
    assertTrue(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenZonedDateTimeOutsideBounds() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneOffsets.GMT);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneOffsets.PLUS_ONE);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneOffsets.PLUS_FOUR);

    // Act and assert
    assertFalse(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenZonedDateTimeLowerLimitInclusive() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneOffsets.MINUS_TWO);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneOffsets.PLUS_3);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneOffsets.GMT);

    // Act and assert
    assertTrue(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenZonedDateTimeUpperLimitExclusive() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneOffsets.MINUS_TWO);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneOffsets.PLUS_3);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 13), ZoneOffsets.GMT);

    // Act and assert
    assertFalse(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenWithTimeInterval() throws Exception {
    // Arrange
    LocalDateTime start = LocalDateTime.of(2018, 9, 26, 10, 13);
    LocalDateTime end = LocalDateTime.of(2018, 9, 26, 12, 13);
    TimeInterval timeInterval = new TimeInterval(start, end);

    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneOffsets.PLUS_FOUR);

    // Act and assert
    assertTrue(zonedDateTimeUtil.isBetween(zonedDateTime, timeInterval));
  }

  @Test
      public void testConvertFromEpochTimeToUtcOffset() throws Exception {
        // Arrange
        long epochTime = 1537976456L;
        ZonedDateTime expectedZonedDateTime =
            ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 40, 56), ZoneOffset.UTC);
    
        // Act
        ZonedDateTime actualZonedDateTime =
            zonedDateTimeUtil.convertFromEpochTimeToUtcOffset(epochTime);
    
        // Assert
        assertEquals(expectedZonedDateTime, actualZonedDateTime);
      }

  @Test
  public void testConvertFromEpochTimeToSystemDefaultZoneId() throws Exception {
    // Arrange
    PowerMockito.mockStatic(ZoneId.class);

    PowerMockito.when(ZoneId.systemDefault()).thenReturn(CUSTOM_USER_TIMEZONE);

    long epochTime = 1581480132L;
    ZonedDateTime expectedZonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2020, 2, 12, 7, 2, 12), CUSTOM_USER_TIMEZONE);

    // Act
    ZonedDateTime actualZonedDateTime =
        zonedDateTimeUtil.convertFromEpochTimeToSystemDefaultZoneId(epochTime);

    // Assert
    assertEquals(expectedZonedDateTime, actualZonedDateTime);
  }

  @Test
    public void testToStringAsIsoOffsetDateTime() throws Exception {
      // Arrange
      String expectedFormattedZonedDateTime = "2018-09-26T15:52:59-03:00";
      ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 52, 59),
          ZoneOffsets.PLUS_3.normalized());
  
      // Act
      String actualFormattedZonedDateTime =
          zonedDateTimeUtil.toStringAsIsoOffsetDateTime(zonedDateTime);
  
      // Assert
      assertEquals(expectedFormattedZonedDateTime, actualFormattedZonedDateTime);
    }

  @Test
    public void testParseFromIsoOffsetFormat() throws Exception {
      // Arrange
      String textDate = "2018-09-26T15:52:59-03:00";
      ZonedDateTime expectedZonedDateTime =
          ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 52, 59), ZoneOffsets.PLUS_3);
  
      // Act
      ZonedDateTime actualZonedDateTime = zonedDateTimeUtil.parseFromIsoOffsetFormat(textDate);
  
      // Assert
      assertEquals(expectedZonedDateTime, actualZonedDateTime);
    }

  @Test
  public void testConvertAsEpochTime() throws Exception {
    // Arrange
    long expectedEpochTime = 1538010257L;
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 16, 4, 17), ZoneOffsets.PLUS_NINE);

    // Act
    long actualEpochTime = zonedDateTimeUtil.convertAsEpochTime(zonedDateTime);

    // Assert
    assertEquals(expectedEpochTime, actualEpochTime);
  }

  /*
   * @Test public void testParseFromTimestamp() throws Exception { // Arrange String textDate =
   * "2018-09-26T15:52:59.612-0300"; ZonedDateTime expectedZoneDateTime = ZonedDateTime
   * .of(LocalDateTime.of(2018, 9, 26, 15, 52, 59, 612000000), ZoneIds.GMT_PLUS_3.normalized());
   * 
   * // Act ZonedDateTime actualZonedDateTime = zonedDateTimeUtil.parseFromTimestamp(textDate);
   * 
   * // Assert assertEquals(expectedZoneDateTime, actualZonedDateTime); }
   */

  /*
   * @Test public void testToSystemDefaultZoneId() throws Exception { // Arrange ZonedDateTime
   * zonedDateTime = ZonedDateTime.of(2019, 8, 30, 10, 26, 7, 0, ZoneIds.GMT_PLUS_ONE);
   * ZonedDateTime expectedResult = ZonedDateTime.of(2019, 8, 30, 14, 26, 7, 0, CUSTOM_ZONE_ID);
   * 
   * // Act ZonedDateTime actualResult = zonedDateTimeUtil.toSystemDefaultZoneId(zonedDateTime);
   * 
   * // Assert assertThat(actualResult).isEqualTo(expectedResult); }
   */

  private static final class ZoneOffsets {

    private static final ZoneOffset PLUS_NINE = ZoneOffset.ofHours(-9);

    private static final ZoneOffset MINUS_TWO = ZoneOffset.ofHours(2);

    private static final ZoneOffset GMT = ZoneOffset.ofHours(0);

    private static final ZoneOffset PLUS_ONE = ZoneOffset.ofHours(-1);

    private static final ZoneOffset PLUS_3 = ZoneOffset.ofHours(-3);

    private static final ZoneOffset PLUS_FOUR = ZoneOffset.ofHours(-4);

  }

}
