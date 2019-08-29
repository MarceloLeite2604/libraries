package com.github.marceloleite2604.util.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

public class TimeIntervalTest {

	@Test
	public void testTimeIntervalZonedDateTimeZonedDateTime() throws Exception {
		// Arrange
		ZonedDateTime start = ZonedDateTime.of(2019, 8, 1, 0, 0, 0, 0, ZoneId.systemDefault());
		ZonedDateTime end = ZonedDateTime.of(2019, 8, 2, 12, 30, 15, 200000,
				ZoneId.systemDefault());
		Duration expectedDuration = Duration.ofDays(1)
				.plusHours(12)
				.plusMinutes(30)
				.plusSeconds(15)
				.plusNanos(200000);

		TimeInterval timeInterval = new TimeInterval(start, end);

		// Act
		Duration actualDuration = timeInterval.getDuration();

		// Assert
		assertThat(actualDuration).isEqualTo(expectedDuration);
	}

	@Test
	public void testTimeIntervalZonedDateTimeDuration() throws Exception {
		// Arrange
		ZonedDateTime start = ZonedDateTime.of(2019, 8, 1, 0, 0, 0, 0, ZoneId.systemDefault());
		Duration duration = Duration.ofDays(1)
				.plusHours(6)
				.plusMinutes(20)
				.plusSeconds(40)
				.plusNanos(40000);

		ZonedDateTime expectedEnd = ZonedDateTime.of(2019, 8, 2, 6, 20, 40, 40000,
				ZoneId.systemDefault());

		TimeInterval timeInterval = new TimeInterval(start, duration);

		// Act
		ZonedDateTime actualEnd = timeInterval.getEnd();

		// Assert
		assertThat(actualEnd).isEqualTo(expectedEnd);
	}

	@Test
	public void testTimeIntervalDurationZonedDateTime() throws Exception {
		// Arrange

		Duration duration = Duration.ofDays(1)
				.plusHours(6)
				.plusMinutes(20)
				.plusSeconds(40)
				.plusNanos(40000);

		ZonedDateTime end = ZonedDateTime.of(2019, 5, 7, 6, 20, 40, 40000, ZoneId.systemDefault());

		ZonedDateTime expectedStart = ZonedDateTime.of(2019, 5, 6, 0, 0, 0, 0,
				ZoneId.systemDefault());

		TimeInterval timeInterval = new TimeInterval(duration, end);

		// Act
		ZonedDateTime actualStart = timeInterval.getStart();

		// Assert
		assertThat(actualStart).isEqualTo(expectedStart);
	}

	@Test
	public void testCompareToShouldReturnNegativeValueWhenFirstTimeStartsBeforeSecondTime() throws Exception {
		// Arrange
		ZonedDateTime firstTimeIntervalStart = ZonedDateTime.of(2019, 9, 1, 12, 40, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime firstTimeIntervalEnd = ZonedDateTime.of(2019, 9, 2, 3, 0, 0, 0,
				ZoneId.systemDefault());

		ZonedDateTime secondTimeIntervalStart = ZonedDateTime.of(2019, 9, 1, 12, 40, 0, 100,
				ZoneId.systemDefault());

		Duration secondTimeIntervalDuration = Duration.ofDays(2)
				.plusHours(2)
				.plusMinutes(14)
				.plusSeconds(30);

		TimeInterval firstTimeInterval = new TimeInterval(firstTimeIntervalStart,
				firstTimeIntervalEnd);

		TimeInterval secondTimeInterval = new TimeInterval(secondTimeIntervalStart,
				secondTimeIntervalDuration);

		// Act
		int actualResult = firstTimeInterval.compareTo(secondTimeInterval);

		// Assert
		assertThat(actualResult).isLessThan(0);
	}
	
	@Test
	public void testCompareToShouldReturnPositiveValueWhenFirstTimeStartsAfterSecondTime() throws Exception {
		// Arrange
		ZonedDateTime firstTimeIntervalStart = ZonedDateTime.of(2019, 9, 1, 12, 40, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime firstTimeIntervalEnd = ZonedDateTime.of(2019, 9, 2, 3, 0, 0, 0,
				ZoneId.systemDefault());

		ZonedDateTime secondTimeIntervalStart = ZonedDateTime.of(2019, 9, 1, 12, 39, 59, 999999,
				ZoneId.systemDefault());

		Duration secondTimeIntervalDuration = Duration.ofDays(2)
				.plusHours(2)
				.plusMinutes(14)
				.plusSeconds(30);

		TimeInterval firstTimeInterval = new TimeInterval(firstTimeIntervalStart,
				firstTimeIntervalEnd);

		TimeInterval secondTimeInterval = new TimeInterval(secondTimeIntervalStart,
				secondTimeIntervalDuration);

		// Act
		int actualResult = firstTimeInterval.compareTo(secondTimeInterval);

		// Assert
		assertThat(actualResult).isGreaterThan(0);
	}
	
	@Test
	public void testCompareToShouldReturnZeroWhenFirstTimeStartsAtSameTimeThanSecondTime() throws Exception {
		// Arrange
		ZonedDateTime timeIntervalStart = ZonedDateTime.of(2019, 9, 1, 12, 40, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime firstTimeIntervalEnd = ZonedDateTime.of(2019, 9, 2, 3, 0, 0, 0,
				ZoneId.systemDefault());

		Duration secondTimeIntervalDuration = Duration.ofDays(2)
				.plusHours(2)
				.plusMinutes(14)
				.plusSeconds(30);

		TimeInterval firstTimeInterval = new TimeInterval(timeIntervalStart,
				firstTimeIntervalEnd);

		TimeInterval secondTimeInterval = new TimeInterval(timeIntervalStart,
				secondTimeIntervalDuration);

		// Act
		int actualResult = firstTimeInterval.compareTo(secondTimeInterval);

		// Assert
		assertThat(actualResult).isEqualTo(0);
	}

}
