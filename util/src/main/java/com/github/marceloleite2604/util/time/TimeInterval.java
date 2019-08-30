package com.github.marceloleite2604.util.time;

import java.time.Duration;
import java.time.ZonedDateTime;

public class TimeInterval implements Comparable<TimeInterval> {

	private ZonedDateTime start;

	private ZonedDateTime end;

	private Duration duration;

	public TimeInterval(ZonedDateTime start, ZonedDateTime end) {
		super();
		this.start = start;
		this.end = end;
		this.duration = Duration.between(start, end);
	}

	public TimeInterval(ZonedDateTime start, Duration duration) {
		super();
		this.start = start;
		this.duration = duration;
		this.end = start.plus(duration);
	}

	public TimeInterval(Duration duration, ZonedDateTime end) {
		super();
		this.duration = duration;
		this.end = end;
		this.start = end.minus(duration);
	}

	public ZonedDateTime getStart() {
		return start;
	}

	public ZonedDateTime getEnd() {
		return end;
	}

	public Duration getDuration() {
		return duration;
	}

	@Override
	public String toString() {
		return ZonedDateTimeUtil.format(start) + " to " + ZonedDateTimeUtil.format(end);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeInterval other = (TimeInterval) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end)) {
			return false;
		}
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(TimeInterval other) {
		if (start.isBefore(other.getStart())) {
			return -1;
		} else if (start.isAfter(other.getStart())) {
			return 1;
		} else {
			return 0;
		}
	}
}
