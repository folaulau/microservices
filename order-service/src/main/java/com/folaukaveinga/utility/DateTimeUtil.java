package com.folaukaveinga.utility;


public class DateTimeUtil {

	// Millisecond Based
	public static final long MILLISECOND = 1000L;
	public static final long SECOND = MILLISECOND;
	public static final long MINUTE = SECOND * 60;
	public static final long HOUR = MINUTE * 60;
	public static final long DAY = HOUR * 24;
	public static final long WEEK = DAY * 7;
	
	/**
	 * Based on Milliseconds
	 * @param hr
	 * @return hours in milliseconds
	 */
	public static long getHoursInMilliseconds(long hr) {
		return HOUR * hr;
	}
}
