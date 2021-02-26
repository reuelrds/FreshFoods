package io.freshfoods.store;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.freshfoods.constants.Constants;
import io.freshfoods.utils.Utils;

/**
 * Servlet implementation class StoreServlet
 */
@WebServlet(name="StoreServlet", urlPatterns= {"/store"})
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreServlet() {
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
		ResultSet result = null;
		
		// Container for storing response
		JsonObject responseData = new JsonObject();
		
		try {

			// Get Database COnnection
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/FreshFoods");
			conn = ds.getConnection();
			
			// Create a Prepared Statement and Execute the Query
			stmt = (PreparedStatement) conn.prepareStatement(Constants.GET_ITEMS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery();
			
			if (!result.next()) {
				responseData.addProperty("message", "Error");
				responseData.addProperty("errorDetails", "Error Retrieveing Items from the Database.");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				
			} else {
				
				// Reset Cursor
				result.beforeFirst();
				
				
				// Create a Container to Store Items
				JsonArray items = new JsonArray();
			
				// Get the Result
				while(result.next()) {
					
						
					// Container for item Details
					JsonObject itemData = new JsonObject();
					
					// Populate the container
					itemData.addProperty("id", result.getString("id"));
					itemData.addProperty("name", result.getString("name"));
					itemData.addProperty("imageUrl", result.getString("imageUrl"));
					itemData.addProperty("price", result.getDouble("price"));
					itemData.addProperty("unit", result.getString("unit"));
					itemData.addProperty("description", result.getString("description"));
					itemData.addProperty("storage", result.getString("storage"));
					itemData.addProperty("origin", result.getString("origin"));
					itemData.addProperty("preparation", result.getString("preparation"));
					
					// Add Item to Items Array
					items.add(itemData);
					
					
				}
				// Populate Final response
				responseData.add("items", items);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		 TODO Auto-generated method stub
		
	}

}
