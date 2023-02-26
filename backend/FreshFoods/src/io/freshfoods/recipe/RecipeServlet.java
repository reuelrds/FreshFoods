package io.freshfoods.recipe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.freshfoods.constants.Constants;

/**
 * Servlet implementation class RecipeServlet
 */
@WebServlet("/recipes")
public class RecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet recipeResult = null;
		ResultSet ingredientResult = null;
		
		
		// Container for storing response
		JsonObject responseData = new JsonObject();
		
		try {

			// Get Database COnnection
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/FreshFoods");
			conn = ds.getConnection();
			
			// Create a Prepared Statement and Execute the Query
			stmt = (PreparedStatement) conn.prepareStatement(Constants.GET_RECIPES, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			recipeResult = stmt.executeQuery();
			
			if (!recipeResult.next()) {
				responseData.addProperty("message", "Error");
				responseData.addProperty("errorDetails", "Error Retrieveing Recipes from the Database.");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				
			} else {
				
				// Reset Cursor
				recipeResult.beforeFirst();
				
				
				// Create a Container to Store Recipes
				JsonArray recipes = new JsonArray();
			
				// Get the Result
				while(recipeResult.next()) {
					
						
					// Container for Recipe
					JsonObject recipeData = new JsonObject();
					
					// Populate the container
					recipeData.addProperty("id", recipeResult.getString("id"));
					recipeData.addProperty("name", recipeResult.getString("name"));
					recipeData.addProperty("imageUrl", recipeResult.getString("imageUrl"));
					recipeData.addProperty("difficulty", recipeResult.getString("difficulty"));
					recipeData.addProperty("prepTime", recipeResult.getInt("prepTime"));
					recipeData.addProperty("cookTime", recipeResult.getInt("cookTime"));
					recipeData.addProperty("servingSize", recipeResult.getInt("servingSize"));
					recipeData.addProperty("calories", recipeResult.getInt("calories"));
					recipeData.addProperty("protein", recipeResult.getInt("protein"));
					recipeData.addProperty("carbohydrates", recipeResult.getInt("carbohydrates"));
					recipeData.addProperty("sugar", recipeResult.getInt("sugar"));
					recipeData.addProperty("fibre", recipeResult.getInt("fibre"));
					recipeData.addProperty("cholestrol", recipeResult.getInt("cholestrol"));
					recipeData.addProperty("sodium", recipeResult.getInt("sodium"));
					recipeData.addProperty("instructions", recipeResult.getString("instructions"));
					
					
					
					// Create a Container to Store Ingredients
					JsonArray ingredients = new JsonArray();
					
					// Create a Prepared Statement and Execute the Query
					stmt = (PreparedStatement) conn.prepareStatement(Constants.GET_RECIPE_INGREDIENTS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					stmt.setString(1, recipeResult.getString("id"));
					ingredientResult = stmt.executeQuery();
					
					if (!ingredientResult.next()) {
						responseData.addProperty("message", "Error");
						responseData.addProperty("errorDetails", "Error Retrieveing Recipe Ingredients from the Database.");
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					} else {
						
						// Reset Cursor
						ingredientResult.beforeFirst();
					
						// Get the Result
						while(ingredientResult.next()) {
							
								
							// Container for item Details
							JsonObject ingredientData = new JsonObject();
							
							// Populate the container
							ingredientData.addProperty("id", ingredientResult.getString("id"));
							ingredientData.addProperty("name", ingredientResult.getString("name"));
							ingredientData.addProperty("imageUrl", ingredientResult.getString("imageUrl"));
							ingredientData.addProperty("price", ingredientResult.getDouble("price"));
							ingredientData.addProperty("unit", ingredientResult.getString("unit"));
							ingredientData.addProperty("itemCount", ingredientResult.getInt("ingredientItemCount"));
							ingredientData.addProperty("description", ingredientResult.getString("description"));
							ingredientData.addProperty("storage", ingredientResult.getString("storage"));
							ingredientData.addProperty("origin", ingredientResult.getString("origin"));
							ingredientData.addProperty("preparation", ingredientResult.getString("preparation"));
							
							
							// Add Item to Items Array
							ingredients.add(ingredientData);
							
							
						}
						
					}
					
					recipeData.add("ingredients", ingredients);
					recipes.add(recipeData);
				}
				
				// Populate Final response
				responseData.add("recipes", recipes);
				responseData.addProperty("message", "Items Retrieved Successfully");
				
				response.setStatus(HttpServletResponse.SC_OK);
			}
			
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseData.addProperty("message", "Error");
			responseData.addProperty("errorDetails", e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		} finally {
			try {
				
				if (ingredientResult != null) {
					ingredientResult.close();
				}
				
				if (recipeResult != null) {
					recipeResult.close();
					
				}
				stmt.close();
				conn.close();
				initContext.close();
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String responseDataJson = new Gson().toJson(responseData);
		response.getWriter().write(responseDataJson);
		response.getWriter().flush();
	}

}
