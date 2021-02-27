package io.freshfoods.profile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/api/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Set Response Type Header
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		
		// Parse POST Data
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		System.out.println(data);
		
		// Retrieve UserId and AddressId
		String id = data.get("id").getAsString();
		
		String retrievedAddressID = data.get("addressId").getAsString();
		String addressId;
		
		// Create AddressId if it is empty
		if (retrievedAddressID.equals("")) {
			addressId = UUID.randomUUID().toString();
		} else {
			addressId = retrievedAddressID;
		}
		
		System.out.println("AddressId: " + addressId);

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
			
			// Update User
			stmt = (PreparedStatement) conn.prepareStatement(Constants.UPDATE_USER);
			stmt.setString(1, getStringData(data, "name"));
			stmt.setString(2, getStringData(data, "email"));
			stmt.setString(3, getStringData(data, "phone"));
			stmt.setString(4, id);
			
			stmt.executeUpdate();
			
			
			
			// Update User Address
			stmt = (PreparedStatement) conn.prepareStatement(Constants.INSERT_USER_ADDRESS);
			stmt.setString(1, addressId);
			stmt.setString(2, getStringData(data, "addressLine1"));
			stmt.setString(3, getStringData(data, "addressLine2"));
			stmt.setString(4, getStringData(data, "city"));
			stmt.setString(5, getStringData(data, "state"));
			stmt.setString(6, getStringData(data, "zipcode"));
			stmt.setString(7, id);
			stmt.setString(8, getStringData(data, "addressLine1"));
			stmt.setString(9, getStringData(data, "addressLine2"));
			stmt.setString(10, getStringData(data, "city"));
			stmt.setString(11, getStringData(data, "state"));
			stmt.setString(12, getStringData(data, "zipcode"));
			
			stmt.executeUpdate();

			
			
			
			stmt = (PreparedStatement) conn.prepareStatement(Constants.GET_USER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
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
					
						
					// Container for User Details
					JsonObject userData = new JsonObject();
					
					// Populate the container
					userData.addProperty("id", result.getString("userId"));
					userData.addProperty("name", result.getString("name"));
					userData.addProperty("email", result.getString("email"));
					userData.addProperty("phone", result.getString("phone"));
					userData.addProperty("addressID", result.getString("address.id"));
					userData.addProperty("addressLine1", result.getString("addressLine1"));
					userData.addProperty("addressLine2", result.getString("addressLine2"));
					userData.addProperty("city", result.getString("city"));
					userData.addProperty("state", result.getString("state"));
					userData.addProperty("zipcode", result.getString("zipcode"));
					
				    // Populate Final response
				    responseData.add("user", userData);
				    responseData.addProperty("message", "Update Successfull");
				    
				    response.setStatus(HttpServletResponse.SC_OK);
						
					
					
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
				
				if ( result != null) {
					result.close();					
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
	
	String getStringData(JsonObject data, String key) {
		
		if (data.get(key) != null ) {			
			return data.get(key).getAsString();
		} else {
				
			return "";
		}
		
	}

}
