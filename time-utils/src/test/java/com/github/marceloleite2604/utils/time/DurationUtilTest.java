package com.github.marceloleite2604.utils.time;

import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.junit.Test;

import com.github.marceloleite2604.utils.time.DurationUtil;

public class DurationUtilTest {

	@Test
	public void testFormatAsSpelledNumber() throws Exception {
		// Arrange
		String expectedSpelledNumber = "7 days, 5 hours, 32 minutes, 9 seconds and 230596 nanoseconds";
		Duration duration = Duration.ofDays(7)
				.plusHours(5)
				.plusMinutes(32)
				.plusSeconds(9)
				.plusNanos(230596);

		// Act
		String actualSpelledNumber = DurationUtil.formatAsSpelledNumber(duration);

		// Assert
		assertEquals(expectedSpelledNumber, actualSpelledNumber);
	}

	@Test
	public void testFormatAsSpelledNumberSingularUnits() throws Exception {
		// Arrange
		String expectedSpelledNumber = "1 day, 1 hour, 1 minute, 1 second and 1 nanosecond";
		Duration duration = Duration.ofDays(1)
				.plusHours(1)
				.plusMinutes(1)
				.plusSeconds(1)
				.plusNanos(1);

		// Act
		String actualSpelledNumber = DurationUtil.formatAsSpelledNumber(duration);

		// Assert
		assertEquals(expectedSpelledNumber, actualSpelledNumber);
	}

	@Test
	public void testFormatAsSeconds() throws Exception {
		// Arrange
		Double expectedSeconds = 653997.368329561;
		Duration duration = Duration.ofDays(7)
				.plusHours(13)
				.plusMinutes(39)
				.plusSeconds(57)
				.plusNanos(368329561L);

		// Act
		Double actualSeconds = DurationUtil.formatAsSeconds(duration);

		// Assert
		assertEquals(expectedSeconds, actualSeconds, 0.000000001);
	}

	@Test
	public void testParseFromSeconds() throws Exception {
		// Arrange
		Duration expectedDuration = Duration.ofDays(316)
				.plusHours(21)
				.plusMinutes(42)
				.plusSeconds(1)
				.plusNanos(364788055);
		double seconds = 27380521.364788055;

		// Act
		Duration actualDuration = DurationUtil.parseFromSeconds(seconds);

		// Assert
		assertEquals(expectedDuration.getSeconds(), actualDuration.getSeconds());
		assertEquals(expectedDuration.getNano(), actualDuration.getNano());
	}

}
