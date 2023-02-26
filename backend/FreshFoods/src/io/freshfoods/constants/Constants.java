package io.freshfoods.constants;


public class Constants {
	public static final String JWTSecret = "Lorem Ipsum Dolor Sit Amet";
	public static final String JWTIssuer = "Fresh Foods";
	public static final long EXPIRATION_TIME = 360000; // 1 hour

	public static final String GET_USER_FROM_ID = "SELECT * FROM User WHERE id = ?";
	public static final String GET_USER_FROM_EMAIL = "SELECT * FROM User WHERE email = ?";
	public static final String INSERT_USER = "INSERT INTO User (id, name, email, password) values (?, ?, ?, ?)";
	public static final String UPDATE_USER = "UPDATE User SET name = ?, email = ?, phone = ?, addressLine1 = ?, addressLine2 = ?, city = ?, state = ?, zipcode = ? WHERE id = ?";
	
	public static final String GET_ITEMS = "SELECT * FROM Item";
	
	public static final String GET_RECIPES = "SELECT Recipe.*, COUNT(recipeingredient.ingredientId) as ingredientCount FROM RECIPE INNER JOIN RecipeIngredient ON Recipe.id = recipeingredient.recipeId GROUP BY Recipe.id";
	public static final String GET_RECIPE_INGREDIENTS = "SELECT Item.*,  RecipeIngredient.ingredientItemCount FROM Item INNER JOIN RecipeIngredient ON RecipeIngredient.ingredientId = Item.id WHERE RecipeIngredient.recipeId = ?";
}
