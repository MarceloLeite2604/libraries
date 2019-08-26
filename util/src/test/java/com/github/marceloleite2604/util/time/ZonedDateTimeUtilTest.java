package com.github.marceloleite2604.util.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import com.github.marceloleite2604.util.time.TimeInterval;
import com.github.marceloleite2604.util.time.ZonedDateTimeUtil;

public class ZonedDateTimeUtilTest {

	@Test
	public void testNow() throws Exception {
		// Act and assert
		assertNotNull(ZonedDateTimeUtil.now());
	}

	@Test
	public void testIsBetweenZonedDateTimeInsideBounds() throws Exception {
		// Arrange
		ZonedDateTime lowerLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT+3"));
		ZonedDateTime upperLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneId.of("Etc/GMT+3"));
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT+4"));

		// Act and assert
		assertTrue(ZonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
	}

	@Test
	public void testIsBetweenZonedDateTimeOutsideBounds() throws Exception {
		// Arrange
		ZonedDateTime lowerLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT0"));
		ZonedDateTime upperLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneId.of("Etc/GMT+1"));
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT+4"));

		// Act and assert
		assertFalse(ZonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
	}

	@Test
	public void testIsBetweenZonedDateTimeLowerLimitInclusive() throws Exception {
		// Arrange
		ZonedDateTime lowerLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT-2"));
		ZonedDateTime upperLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneId.of("Etc/GMT+3"));
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneId.of("Etc/GMT0"));

		// Act and assert
		assertTrue(ZonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
	}

	@Test
	public void testIsBetweenZonedDateTimeUpperLimitExclusive() throws Exception {
		// Arrange
		ZonedDateTime lowerLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT-2"));
		ZonedDateTime upperLimit = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneId.of("Etc/GMT+3"));
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 13), ZoneId.of("Etc/GMT0"));

		// Act and assert
		assertFalse(ZonedDateTimeUtil.isBetween(zonedDateTime, lowerLimit, upperLimit));
	}

	@Test
	public void testIsBetweenWithTimeInterval() throws Exception {
		// Arrange
		ZonedDateTime start = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT+3"));
		ZonedDateTime end = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 12, 13), ZoneId.of("Etc/GMT+3"));
		TimeInterval timeInterval = new TimeInterval(start, end);

		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 13), ZoneId.of("Etc/GMT+4"));

		// Act and assert
		assertTrue(ZonedDateTimeUtil.isBetween(zonedDateTime, timeInterval));
	}

	@Test
	public void testConvertFromEpochTime() throws Exception {
		// Arrange
		long epochTime = 1537976456L;
		ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 40, 56),
				ZonedDateTimeUtil.DEFAULT_ZONE_ID);

		// Act
		ZonedDateTime actualZonedDateTime = ZonedDateTimeUtil.convertFromEpochTime(epochTime);

		// Assert
		assertEquals(expectedZonedDateTime, actualZonedDateTime);
	}

	@Test
	public void testFormat() throws Exception {
		// Arrange
		String expectedFormattedZonedDateTime = "2018-09-26T15:52:59-03:00";
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 52, 59), ZoneId.of("Etc/GMT+3")
				.normalized());

		// Act
		String actualFormattedZonedDateTime = ZonedDateTimeUtil.format(zonedDateTime);

		// Assert
		assertEquals(expectedFormattedZonedDateTime, actualFormattedZonedDateTime);
	}

	@Test
	public void testParse() throws Exception {
		// Arrange
		String textDate = "2018-09-26T15:52:59-03:00";
		ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 52, 59),
				ZoneId.of("Etc/GMT+3")
						.normalized());

		// Act
		ZonedDateTime actualZonedDateTime = ZonedDateTimeUtil.parse(textDate);

		// Assert
		assertEquals(expectedZonedDateTime, actualZonedDateTime);
	}

	@Test
	public void testFormatAsEpochTime() throws Exception {
		// Arrange
		long expectedEpochTime = 1537945457L;
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 16, 4, 17),
				ZoneId.of("Etc/GMT-9"));

		// Act
		long actualEpochTime = ZonedDateTimeUtil.formatAsEpochTime(zonedDateTime);

		// Assert
		assertEquals(expectedEpochTime, actualEpochTime);
	}

	@Test
	public void testToDefaultZoneId() throws Exception {
		// Arrange
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 16, 4, 17),
				ZoneId.of("Etc/GMT-9"));
		ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 7, 4, 17),
				ZoneId.of("UTC"));

		// Act
		ZonedDateTime actualZonedDateTime = ZonedDateTimeUtil.toDefaultZoneId(zonedDateTime);

		// Assert
		assertEquals(expectedZonedDateTime, actualZonedDateTime);
	}

	@Test
	public void testFormatAsTimestamp() throws Exception {
		// Arrange
		String expectedTextDate = "2018-09-26T15:52:59.612-0300";
		ZonedDateTime zoneDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 52, 59, 612000000),
				ZoneId.of("Etc/GMT+3")
						.normalized());

		// Act
		String actualTextDate = ZonedDateTimeUtil.formatAsTimestamp(zoneDateTime);

		// Assert
		assertEquals(expectedTextDate, actualTextDate);
	}

	@Test
	public void testFormatFromWrittenTimestamp() throws Exception {
		// Arrange
		String textDate = "2018-09-26T15:52:59.612-0300";
		ZonedDateTime expectedZoneDateTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 15, 52, 59, 612000000),
				ZoneId.of("Etc/GMT+3")
						.normalized());

		// Act
		ZonedDateTime actualZonedDateTime = ZonedDateTimeUtil.convertFromWrittenTimestamp(textDate);

		// Assert
		assertEquals(expectedZoneDateTime, actualZonedDateTime);
	}

}
