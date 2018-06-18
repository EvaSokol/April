package com.april.testproject.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Eva Sokolyanskaya on 14/06/2018.
 */
public class ApiUtils {

	public static String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}


}
