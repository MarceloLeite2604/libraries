package org.marceloleite.libraries.time.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.Test;

public class LocalTimeUtilTest {

	@Test
	public void testIsBetweenInsideLimits() throws Exception {
		// Arrange
		LocalTime timeToCheck = LocalTime.of(13, 17);
		LocalTime startTime = LocalTime.of(12, 0);
		Duration duration = Duration.ofHours(4);

		// Act and assert
		assertTrue(LocalTimeUtil.isBetween(timeToCheck, startTime, duration));
	}
	
	@Test
	public void testIsBetweenOutsideLimits() throws Exception {
		// Arrange
		LocalTime timeToCheck = LocalTime.of(0, 2);
		LocalTime startTime = LocalTime.of(0, 0);
		Duration duration = Duration.ofMinutes(1);

		// Act and assert
		assertFalse(LocalTimeUtil.isBetween(timeToCheck, startTime, duration));
	}
	
	@Test
	public void testIsBetweenUpperLimitExclusive() throws Exception {
		// Arrange
		LocalTime timeToCheck = LocalTime.of(16, 0);
		LocalTime startTime = LocalTime.of(12, 0);
		Duration duration = Duration.ofHours(4);

		// Act and assert
		assertFalse(LocalTimeUtil.isBetween(timeToCheck, startTime, duration));
	}
	
	@Test
	public void testIsBetweenLowerLimitInclusive() throws Exception {
		// Arrange
		LocalTime timeToCheck = LocalTime.of(12, 0);
		LocalTime startTime = LocalTime.of(12, 0);
		Duration duration = Duration.ofHours(1);

		// Act and assert
		assertTrue(LocalTimeUtil.isBetween(timeToCheck, startTime, duration));
	}

	@Test
	public void testParse() throws Exception {
		// Arrange
		String timeText = "17:58:00";
		LocalTime expectedLocalTime = LocalTime.of(17, 58);

		// Act
		LocalTime actualLocalTime = LocalTimeUtil.parse(timeText);

		// Assert
		assertEquals(expectedLocalTime, actualLocalTime);
	}

	@Test
	public void testFormat() throws Exception {
		// Arrange
		LocalTime localTime = LocalTime.of(6, 47, 13);
		String expectedFormattedText = "06:47:13";

		// Act
		String actualFormattedText = LocalTimeUtil.format(localTime);

		// Assert
		assertEquals(expectedFormattedText, actualFormattedText);
	}

}
