package io.freshfoods.auth;

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

import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.freshfoods.constants.Constants;
import io.freshfoods.utils.Utils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="LoginServlet", urlPatterns={"/auth/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Set Response Type Header
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		
		// Parse POST Data
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
//		System.out.println(data);
		
		// Retrieve Email and Password
		String email = data.get("email").getAsString();
		String password = data.get("password").getAsString();

		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		// Container for storing response
		JsonObject responseData = new JsonObject();
		
		try {

			// Get Database COnnection
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/FreshFoods");
			conn = ds.getConnection();
			
			stmt = (PreparedStatement) conn.prepareStatement(Constants.GET_USER_FROM_EMAIL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, email);
			
			result = stmt.executeQuery();
			
			if (!result.next()) {
				responseData.addProperty("message", "Error");
				responseData.addProperty("errorDetails", "Invalid Email or Password");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				
			} else {
				
				// Reset Cursor
				result.beforeFirst();
			
				// Get the Result
				while(result.next()) {
					
					// Retrieve User
					String retrievedPassword= result.getString("password");
					
					// Check if Password hash Match
					if (BCrypt.checkpw(password, retrievedPassword)) {
						
						// Container for User Details
						JsonObject userData = new JsonObject();
						
						// Populate teh container
						userData.addProperty("id", result.getString("id"));
						userData.addProperty("name", result.getString("name"));
						userData.addProperty("email", result.getString("email"));
						userData.addProperty("phone", result.getString("phone"));
						userData.addProperty("addressLine1", result.getString("addressLine1"));
						userData.addProperty("addressLine2", result.getString("addressLine2"));
						userData.addProperty("city", result.getString("city"));
						userData.addProperty("state", result.getString("state"));
						userData.addProperty("zipcode", result.getString("zipcode"));
						
						// Get JWT Token
						String jwtToken = Utils.getJWTToken();

						
						// Set Authorization Header
					    response.setHeader("Authorization", "Bearer " + jwtToken);
					    
					    // Populate Final response
					    responseData.add("user", userData);
					    responseData.addProperty("message", "Login Successfull");
					    responseData.addProperty("jwtToken", jwtToken);
					    responseData.addProperty("expiresin", Constants.EXPIRATION_TIME);
					    
					    response.setStatus(HttpServletResponse.SC_OK);
						
					} else {
						
						// Send Error Message When password Check Fails
						responseData.addProperty("message", "Error");
						responseData.addProperty("errorDetails", "Invalid Email or Password");
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					}
					
					
				}
			}
			
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseData.addProperty("message", "Error");
			responseData.addProperty("errorDetails", e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		} finally {
			try {
				result.close();
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
