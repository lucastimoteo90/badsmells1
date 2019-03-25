/*
 * aTunes 1.8.2
 * Copyright (C) 2006-2008 Alex Aranda, Sylvain Gaudard, Thomas Beckers and contributors
 *
 * See http://www.atunes.org/?page_id=7 for information about contributors
 *
 * http://www.atunes.org
 * http://sourceforge.net/projects/atunes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package net.sourceforge.atunes.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for Strings
 */
public class StringUtils {

	/**
	 * Kilobyte 1024 bytes
	 */
	private static final long KILOBYTE = 1024;

	/**
	 * Megabyte 1024 Kilobytes
	 */
	private static final long MEGABYTE = KILOBYTE * 1024;

	/**
	 * Gigabyte 1024 Megabytes
	 */
	private static final long GIGABYTE = MEGABYTE * 1024;

	/**
	 * Seconds in a minute
	 */
	private static final long SECONDS_MINUTE = 60;

	/**
	 * Seconds in an hour
	 */
	private static final long SECONDS_HOUR = 3600;

	/**
	 * Seconds in a day
	 */
	private static final long SECONDS_DAY = 86400;

	/**
	 * Given an amount of bytes, return a string representation in Bytes,
	 * Kilobytes, Megabytes or Gigabytes Examples: Given 1024 bytes -> "1KB"
	 * Given 1536 bytes -> 1.5KB"
	 * 
	 * @param size
	 *            amount of bytes
	 * @return String representation in Bytes, Kilobytes, Megabytes or Gigabytes
	 */
	public static String fromByteToMegaOrGiga(long size) {
		if (size < KILOBYTE)
			return StringUtils.getString(String.valueOf(size), " bytes");
		else if (size < MEGABYTE)
			return StringUtils.getString(toString((double) size / KILOBYTE, 2), " KB");
		else if (size < GIGABYTE)
			return StringUtils.getString(toString((double) size / MEGABYTE, 2), " MB");
		else
			return StringUtils.getString(toString((double) size / GIGABYTE, 2), " GB");
	}

	/**
	 * Given an amount of seconds, returns a string representation in minutes,
	 * hours and days
	 * 
	 * @param s
	 *            seconds
	 * @return a string representation in minutes, hours and days
	 */
	public static String fromSecondsToHoursAndDays(long s) {
		long seconds = s;

		long days = seconds / SECONDS_DAY;
		seconds = seconds % SECONDS_DAY;
		long hours = seconds / SECONDS_HOUR;
		seconds = seconds % SECONDS_HOUR;
		long minutes = seconds / SECONDS_MINUTE;
		seconds = seconds % SECONDS_MINUTE;

		String hoursMinutesSeconds = StringUtils.getString(hours, ":", (minutes < 10 ? "0" : ""), minutes, ":", (seconds < 10 ? "0" : ""), seconds);
		if (days == 1)
			return StringUtils.getString(days, " ", LanguageTool.getString("DAY"), " ", hoursMinutesSeconds);
		else if (days > 1)
			return StringUtils.getString(days, " ", LanguageTool.getString("DAYS"), " ", hoursMinutesSeconds);
		else
			return hoursMinutesSeconds;
	}

	/**
	 * 

	 * Returns a List containing strings of the array. Text between " chars, are
	 * returned on a string
	 * 


	 * 

	 * Example: {"This", "is\"", "a ", "test\""} will return: "This" "is" "a
	 * test"
	 * 


	 * 
	 * @param str
	 *            String array
	 * @return List containing strings of the array
	 */
	public static List fromStringArrayToList(String... str) {
		List result = new ArrayList();
		boolean openedQuotes = false;
		String auxStr = "";
		for (String s : str) {
			if (s.startsWith("\"") && s.endsWith("\""))
				result.add(s.replaceAll("\"", ""));
			else if (s.endsWith("\"")) {
				openedQuotes = false;
				auxStr = StringUtils.getString(auxStr, " ", s.replaceAll("\"", ""));
				result.add(auxStr);
			} else if (s.startsWith("\"")) {
				openedQuotes = true;
				auxStr = s.replaceFirst("\"", "");
			} else if (openedQuotes)
				auxStr = StringUtils.getString(auxStr, " ", s);
			else
				result.add(s);
		}
		return result;
	}

	/**
	 * Returns a string with concatenation of argument array
	 * 
	 * @param strings
	 *            strings
	 * @return concatenation of argument array
	 */
	public static String getString(Object... strings) {
		StringBuilder objStringBuilder = new StringBuilder();

		for (Object element : strings)
			objStringBuilder.append(element);

		return objStringBuilder.toString();
	}

	/**
	 * Returns a double value as a string with a given number of decimal digits
	 * 
	 * @param value
	 *            double value
	 * @param numberOfDecimals
	 *            number of decimal digits
	 * @return string with a given number of decimal digits
	 */
	public static String toString(double value, int numberOfDecimals) {
		String result = Double.toString(value);
		if (result.contains(".")) {
			int nDecimals = Math.min(numberOfDecimals, result.length() - result.indexOf('.'));
			if (result.indexOf('.') + nDecimals + 1 < result.length())
				return result.substring(0, result.indexOf('.') + nDecimals + 1);
			return result.substring(0, result.length());

		}
		return result;
	}
}