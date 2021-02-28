package io.freshfoods.constants;

public class Constants {

  public static final String JWTSecret = "Lorem Ipsum Dolor Sit Amet";
  public static final String JWTIssuer = "Fresh Foods";
  public static final long EXPIRATION_TIME = 3600000; // 1 hour

//  public static final String GET_USER_FROM_ID = "SELECT * FROM User WHERE id = ?";
  public static final String GET_USER = "SELECT * FROM User, Address WHERE User.id = Address.userId";
  public static final String GET_USER_FROM_EMAIL = "SELECT * FROM User LEFT JOIN address ON User.id = Address.userId WHERE User.email = ?";
  public static final String INSERT_USER = "INSERT INTO User (id, name, email, password) values (?, ?, ?, ?)";
  public static final String UPDATE_USER = "UPDATE User SET name = ?, email = ?, phone = ? WHERE id = ?";

  public static final String GET_USER_ADDRESS = "SELECT id from Address where userId = ?";
  public static final String INSERT_USER_ADDRESS = "INSERT INTO Address values(?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `addressLine1` = ?, `addressLine2` = ?, `city` = ?, `state` = ?, `zipcode` = ?";


  public static final String GET_ITEMS = "SELECT * FROM Item";

  public static final String GET_RECIPES = "SELECT Recipe.*, COUNT(recipeingredient.ingredientId) as ingredientCount FROM RECIPE INNER JOIN RecipeIngredient ON Recipe.id = recipeingredient.recipeId GROUP BY Recipe.id";
  public static final String GET_RECIPE_INGREDIENTS = "SELECT Item.*,  RecipeIngredient.ingredientItemCount FROM Item INNER JOIN RecipeIngredient ON RecipeIngredient.ingredientId = Item.id WHERE RecipeIngredient.recipeId = ?";

  public static final String INSERT_DELIVERY_ADDRESS = "INSERT INTO Address (id, addressLine1, addressLine2, city, state, zipcode) values(?, ?, ?, ?, ?, ?)";
  public static final String INSERT_ORDER = "INSERT INTO Orders values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String INSERT_ORDER_ITEMS = "INSERT INTO OrderItems values(?, ?, ?)";

  public static final String GET_ORDERS = "SELECT * FROM Orders INNER JOIN Address ON Orders.deliveryAddressId = Address.id WHERE Orders.userId = ?";
  public static final String GET_ORDER_ITEMS = "SELECT Item.*,  OrderItems.itemCount FROM Item INNER JOIN OrderItems ON OrderItems.itemId = Item.id WHERE OrderItems.orderId = ?";
}
