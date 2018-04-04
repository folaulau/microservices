package com.folaukaveinga.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author fkaveinga
 *
 */
public final class PasswordUtil {
	private final static Logger log = LoggerFactory.getLogger(PasswordUtil.class.getName());
	
	private static final int PASSWORD_LENGTH = 5;
	private static final int PASSWORD_MIN_LENGTH = 7;
	private static final int PASSWORD_MAX_LENGTH = 32;
	final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private PasswordUtil() {
	}

	/*
	 * Password for development only
	 */
	public static String hashPassword(final String password) {
		if (password == null || password.length() < 1)
			throw new RuntimeErrorException(null, "Password must not be null");
		else
			return passwordEncoder.encode(password);
	}
	
	public static boolean verify(String password, String hashPassword) {
		return passwordEncoder.matches(password, hashPassword);
	}
	
	public static String getRandomTempPassword() {
		return RandomGeneratorUtil.getAlphaNumeric(PASSWORD_LENGTH);
	}

	public static boolean validatePassword(String password) {
		if (isPasswordCharactersGood(password) && isPasswordLengthGood(password)) {
			return true;
		}else{
			return false;
		}
	}

	private static boolean isPasswordLengthGood(String password) {
		String pwd = password.trim();
		if (pwd.length() >= PASSWORD_MIN_LENGTH && pwd.length() <= PASSWORD_MAX_LENGTH) {
			return true;
		}else{
			log.info("password length is not valid");
			return false;
		}
	}

	private static boolean isPasswordCharactersGood(String password) {
		// one uppercase letter and one number
		String pattern = "(?=.*?[A-Z])(?=.*?[0-9])";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(password);
		
		if(m.find()){
			return true;
		}else{
			log.info("could not find one number and one upper case letter");
			return false;
		}
	}

}
