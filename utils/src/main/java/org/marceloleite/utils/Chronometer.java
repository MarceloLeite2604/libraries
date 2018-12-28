package org.marceloleite.utils;

public class Chronometer {

	private static final long MILLISECONDS_IN_A_SECOND = 1000;

	private static final long MILLISECDONS_IN_A_MINUTE = 60 * MILLISECONDS_IN_A_SECOND;

	private static final long MILLISECONDS_IN_AN_HOUR = 60 * MILLISECDONS_IN_A_MINUTE;

	private long startTime;

	private long duration;

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public long stop() {
		duration = System.currentTimeMillis() - startTime;
		return duration;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long tempo) {
		this.duration = tempo;
	}

	public String toHoursString() {
		long milliseconds = duration;
		long hours = milliseconds / MILLISECONDS_IN_AN_HOUR;
		milliseconds %= MILLISECONDS_IN_AN_HOUR;
		long minutes = milliseconds / MILLISECDONS_IN_A_MINUTE;
		milliseconds %= MILLISECDONS_IN_A_MINUTE;
		long seconds = milliseconds / MILLISECONDS_IN_A_SECOND;
		milliseconds %= MILLISECONDS_IN_A_SECOND; 

		return String.format("%02d:%02d:%02d.%04d", hours, minutes, seconds, milliseconds);
	}

	public String toSecondsString() {
		long milliseconds = duration;
		long seconds = milliseconds / MILLISECONDS_IN_A_SECOND;
		milliseconds %=  MILLISECONDS_IN_A_SECOND;

		return String.format("%02d.%03d", seconds, milliseconds);
	}
}
