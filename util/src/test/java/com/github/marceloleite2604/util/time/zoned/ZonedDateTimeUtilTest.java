package com.github.marceloleite2604.util.time.zoned;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.marceloleite2604.util.time.TimeInterval;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZonedDateTimeUtilTest {

  private static final String PROPERTY_USER_TIMEZONE = "user.timezonsdgsdge";

  private static final String DEFAULT_USER_TIMEZONE = System.getProperty(PROPERTY_USER_TIMEZONE);

  private static final String CUSTOM_USER_TIMEZONE = "Etc/GMT-3";

  private static final ZoneId CUSTOM_ZONE_ID = ZoneId.of(CUSTOM_USER_TIMEZONE);

  private ZonedDateTimeUtil zonedDateTimeUtil;

  @BeforeClass
  public static void classSetUp() {
    System.setProperty(PROPERTY_USER_TIMEZONE, CUSTOM_USER_TIMEZONE);
  }

  @AfterClass
  public static void classTearDown() {
    if (!Objects.isNull(DEFAULT_USER_TIMEZONE)) {
      System.setProperty(PROPERTY_USER_TIMEZONE, DEFAULT_USER_TIMEZONE);
    } else {
      System.clearProperty(PROPERTY_USER_TIMEZONE);
    }
  }

  @Before
  public void setUp() {
    this.zonedDateTimeUtil = new ZonedDateTimeUtil();
  }

  @Test
  public void testNow() throws Exception {
    // Act
    ZonedDateTime actualResult = zonedDateTimeUtil.now();

    // Assert
    assertThat(actualResult).isNotNull();
  }

  @Test
  public void testIsBetweenZonedDateTimeInsideBounds() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT_PLUS_3);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneIds.GMT_PLUS_3);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT_PLUS_FOUR);

    // Act and assert
    assertTrue(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenZonedDateTimeOutsideBounds() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneIds.GMT_PLUS_ONE);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT_PLUS_FOUR);

    // Act and assert
    assertFalse(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenZonedDateTimeLowerLimitInclusive() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT_MINUS_TWO);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneIds.GMT_PLUS_3);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneIds.GMT);

    // Act and assert
    assertTrue(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenZonedDateTimeUpperLimitExclusive() throws Exception {
    // Arrange
    ZonedDateTime lowerLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT_MINUS_TWO);
    ZonedDateTime upperLimit =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneIds.GMT_PLUS_3);
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 13), ZoneIds.GMT);

    // Act and assert
    assertFalse(zonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
  }

  @Test
  public void testIsBetweenWithTimeInterval() throws Exception {
    // Arrange
    ZonedDateTime start =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT_PLUS_3);
    ZonedDateTime end = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneIds.GMT_PLUS_3);
    TimeInterval timeInterval = new TimeInterval(start, end);

    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneIds.GMT_PLUS_FOUR);

    // Act and assert
    assertTrue(zonedDateTimeUtil.isBetween(zonedDateTime, timeInterval));
  }

  @Test
  public void testConvertFromEpochTime() throws Exception {
    // Arrange
    long epochTime = 1537976456L;
    ZonedDateTime expectedZonedDateTime = ZonedDateTime
        .of(LocalDateTime.of(2018, 9, 26, 15, 40, 56), ZonedDateTimeUtil.DEFAULT_ZONE_ID);

    // Act
    ZonedDateTime actualZonedDateTime = zonedDateTimeUtil.convertFromEpochTime(epochTime);

    // Assert
    assertEquals(expectedZonedDateTime, actualZonedDateTime);
  }

  @Test
  public void testToString() throws Exception {
    // Arrange
    String expectedFormattedZonedDateTime = "2018-09-26T15:52:59-03:00";
    ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 52, 59),
        ZoneIds.GMT_PLUS_3.normalized());

    // Act
    String actualFormattedZonedDateTime = zonedDateTimeUtil.toString(zonedDateTime);

    // Assert
    assertEquals(expectedFormattedZonedDateTime, actualFormattedZonedDateTime);
  }

  @Test
  public void testParse() throws Exception {
    // Arrange
    String textDate = "2018-09-26T15:52:59-03:00";
    ZonedDateTime expectedZonedDateTime = ZonedDateTime
        .of(LocalDateTime.of(2018, 9, 26, 15, 52, 59), ZoneIds.GMT_PLUS_3.normalized());

    // Act
    ZonedDateTime actualZonedDateTime = zonedDateTimeUtil.parse(textDate);

    // Assert
    assertEquals(expectedZonedDateTime, actualZonedDateTime);
  }

  @Test
  public void testToStringAsEpochTime() throws Exception {
    // Arrange
    long expectedEpochTime = 1537945457L;
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 16, 4, 17), ZoneIds.GMT_PLUS_NINE);

    // Act
    long actualEpochTime = zonedDateTimeUtil.convertAsEpochTime(zonedDateTime);

    // Assert
    assertEquals(expectedEpochTime, actualEpochTime);
  }

  @Test
  public void testToDefaultZoneId() throws Exception {
    // Arrange
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 16, 4, 17), ZoneIds.GMT_PLUS_NINE);
    ZonedDateTime expectedZonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 7, 4, 17), ZoneId.of("UTC"));

    // Act
    ZonedDateTime actualZonedDateTime = zonedDateTimeUtil.toDefaultZoneId(zonedDateTime);

    // Assert
    assertEquals(expectedZonedDateTime, actualZonedDateTime);
  }

  @Test
  public void testToStringAsTimestamp() throws Exception {
    // Arrange
    String expectedTextDate = "2018-09-26T15:52:59.612-0300";
    ZonedDateTime zoneDateTime = ZonedDateTime
        .of(LocalDateTime.of(2018, 9, 26, 15, 52, 59, 612000000), ZoneIds.GMT_PLUS_3.normalized());

    // Act
    String actualTextDate = zonedDateTimeUtil.toStringAsTimestamp(zoneDateTime);

    // Assert
    assertEquals(expectedTextDate, actualTextDate);
  }

  @Test
  public void testToStringFromWrittenTimestamp() throws Exception {
    // Arrange
    String textDate = "2018-09-26T15:52:59.612-0300";
    ZonedDateTime expectedZoneDateTime = ZonedDateTime
        .of(LocalDateTime.of(2018, 9, 26, 15, 52, 59, 612000000), ZoneIds.GMT_PLUS_3.normalized());

    // Act
    ZonedDateTime actualZonedDateTime = zonedDateTimeUtil.convertFromWrittenTimestamp(textDate);

    // Assert
    assertEquals(expectedZoneDateTime, actualZonedDateTime);
  }

  @Test
  public void testToSystemDefaultZoneId() throws Exception {
    // Arrange
    ZonedDateTime zonedDateTime = ZonedDateTime.of(2019, 8, 30, 10, 26, 7, 0, ZoneIds.GMT_PLUS_ONE);
    ZonedDateTime expectedResult = ZonedDateTime.of(2019, 8, 30, 14, 26, 7, 0, CUSTOM_ZONE_ID);

    // Act
    ZonedDateTime actualResult = zonedDateTimeUtil.toSystemDefaultZoneId(zonedDateTime);

    // Assert
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  private static final class ZoneIds {

    private static final ZoneId GMT_PLUS_NINE = ZoneId.of("Etc/GMT-9");

    private static final ZoneId GMT_MINUS_TWO = ZoneId.of("Etc/GMT-2");

    private static final ZoneId GMT = ZoneId.of("Etc/GMT0");

    private static final ZoneId GMT_PLUS_ONE = ZoneId.of("Etc/GMT+1");

    private static final ZoneId GMT_PLUS_3 = ZoneId.of("Etc/GMT+3");

    private static final ZoneId GMT_PLUS_FOUR = ZoneId.of("Etc/GMT+4");

  }

}
