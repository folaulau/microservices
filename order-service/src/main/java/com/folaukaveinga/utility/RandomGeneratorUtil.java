package com.folaukaveinga.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * Random Generator Utility is designed to use accross this API - It randomly generate String, Number, and AlphaNumeric
 * @author fkaveinga
 * @see RandomStringUtils
 * @see RandomUtils
 */
public final class RandomGeneratorUtil {
	
	public static String getString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}
	
	public static int getInteger() {
		return RandomUtils.nextInt();
	}
	
	public static int getIntegerWithin(int start, int end) {
		return RandomUtils.nextInt(start, end);
	}
	
	public static Float getFloat() {
		return RandomUtils.nextFloat();
	}
	
	public static Float getIntegerWithin(float start, float end) {
		return RandomUtils.nextFloat(start, end);
	}
	
	public static Double getDouble() {
		return RandomUtils.nextDouble();
	}
	
	public static Double getDoubleWithin(double start, double end) {
		return RandomUtils.nextDouble(start, end);
	}

	public static String getAlphaNumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}
	
	public static boolean getBoolean() {
		return RandomUtils.nextBoolean();
	}
}
