package io.freshfoods.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import io.freshfoods.constants.Constants;

public class Utils {
	
	public static String getJWTToken(String userId) {
		Algorithm algorithm = Algorithm.HMAC256(Constants.JWTSecret);
		String token = JWT.create()
		        .withIssuer(Constants.JWTIssuer)
		        .withSubject(userId)
		        .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
		        .sign(algorithm);
		
		return token;
	}

}
