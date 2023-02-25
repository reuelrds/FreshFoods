package io.freshfoods.constants;


public class Constants {
	public static final String JWTSecret = "Lorem Ipsum Dolor Sit Amet";
	public static final String JWTIssuer = "Fresh Foods";
	public static final long EXPIRATION_TIME = 360000; // 1 hour

	public static final String INSERT_USER = "INSERT INTO User (id, name, email, password) values (?, ?, ?, ?)";
	public static final String GET_USER = "SELECT * FROM User WHERE email = ?";
}
