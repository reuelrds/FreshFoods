package io.freshfoods.order;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.freshfoods.constants.Constants;
import io.freshfoods.order.schema.Address;
import io.freshfoods.order.schema.Item;
import io.freshfoods.order.schema.Order;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(urlPatterns={"/api/order"})
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Set Response Type Header
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet ordersResult = null;
		ResultSet orderItemResult = null;
		
		// JSON Response Data Container
		JsonObject responseData = new JsonObject();
		
		try {
			
			// Get Database COnnection
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/FreshFoods");
			conn = ds.getConnection();
			
			// Get User Id
			String userId = request.getAttribute("userId").toString();
			
			
			// Get All Orders
			stmt = (PreparedStatement) conn.prepareStatement(Constants.GET_ORDERS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, userId);
			
			ordersResult = stmt.executeQuery();
			
			if (!ordersResult.next()) {
				responseData.addProperty("message", "No Orders Placed Yet");
				response.setStatus(HttpServletResponse.SC_OK);
				
			} else {
				
				// Reset Cursor
				ordersResult.beforeFirst();
				
				
				// Create a Container to Store Orders
				JsonArray orders = new JsonArray();
			
				// Get the Result
				while(ordersResult.next()) {
					
						
					// Container for Order
					JsonObject orderData = new JsonObject();
					
					// Populate the Container
					orderData.addProperty("id", ordersResult.getString("id"));
					orderData.addProperty("orderDate", ordersResult.getString("orderDate"));
					orderData.addProperty("transactionId", ordersResult.getString("transactionId"));
					orderData.addProperty("subTotal", ordersResult.getDouble("subTotal"));
					orderData.addProperty("totalPrice", ordersResult.getDouble("totalPrice"));
					orderData.addProperty("totalItemCount", ordersResult.getDouble("totalItemCount"));
				
					// Container for Order Delivery Data
					JsonObject deliveryData = new JsonObject();
					
					// Populate Delivery Data
					deliveryData.addProperty("deliveryDate", ordersResult.getString("deliveryDate"));
					deliveryData.addProperty("deliveryCost", ordersResult.getDouble("deliveryCost"));
					
					// Container for Order Delivery Address
					JsonObject deliveryAddressData = new JsonObject();
					

					
					// Populate Delivery AddressData
					deliveryAddressData.addProperty("addressLine1", ordersResult.getString("addressLine1"));
					deliveryAddressData.addProperty("addressLine2", ordersResult.getString("addressLine2"));
					deliveryAddressData.addProperty("city", ordersResult.getString("city"));
					deliveryAddressData.addProperty("state", ordersResult.getString("state"));
					deliveryAddressData.addProperty("zipcode", ordersResult.getString("zipcode"));
					
					
					// Add delivery Address Data to delivery Date
					deliveryData.add("deliveryAddress", deliveryAddressData);
					
					// Add Delivery Data to order data
					orderData.add("delivery", deliveryData);
					
					
					/**
					 * Get Order Items
					 */
					
					// Create a Container to Store Ingredients
					JsonArray orderItems = new JsonArray();
					
					// Create a Prepared Statement and Execute the Query
					stmt = (PreparedStatement) conn.prepareStatement(Constants.GET_ORDER_ITEMS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					stmt.setString(1, ordersResult.getString("id"));
					orderItemResult = stmt.executeQuery();
					
					
					if (!orderItemResult.next()) {
						responseData.addProperty("message", "Error");
						responseData.addProperty("errorDetails", "Error Retrieveing Order Items from the Database.");
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					} else {
						
						// Reset Cursor
						orderItemResult.beforeFirst();
					
						// Get the Result
						while(orderItemResult.next()) {
							
								
							// Container for item Details
							JsonObject itemData = new JsonObject();
							
							// Populate the container
							itemData.addProperty("id", orderItemResult.getString("id"));
							itemData.addProperty("name", orderItemResult.getString("name"));
							itemData.addProperty("imageUrl", orderItemResult.getString("imageUrl"));
							itemData.addProperty("price", orderItemResult.getDouble("price"));
							itemData.addProperty("unit", orderItemResult.getString("unit"));
							itemData.addProperty("itemCount", orderItemResult.getInt("itemCount"));
							itemData.addProperty("description", orderItemResult.getString("description"));
							itemData.addProperty("storage", orderItemResult.getString("storage"));
							itemData.addProperty("origin", orderItemResult.getString("origin"));
							itemData.addProperty("preparation", orderItemResult.getString("preparation"));
							
							
							// Add Item to Items Array
							orderItems.add(itemData);
//							orderItems.add(itemData);
							
							
						}
						
					}
					
					orderData.add("orderItems", orderItems);
					orders.add(orderData);
				
				}
				
				// Populate Final response
				responseData.add("orders", orders);
				responseData.addProperty("message", "Orders Retrieved Successfully");
				
				response.setStatus(HttpServletResponse.SC_OK);
			
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
				initContext.close();
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				responseData.addProperty("message", "Error");
				responseData.addProperty("errorDetails", e.getMessage());
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
		// TODO Auto-generated method stub
		
		// Set Response Type Header
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		
		// Parse POST Data
		Order order = new Gson().fromJson(request.getReader(), Order.class);
		
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		// JSON Response Data Container
		JsonObject responseData = new JsonObject();
		
		try {
			// Get Database COnnection
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/FreshFoods");
			conn = ds.getConnection();
			
			// Insert Delivery Address
			Address address = order.getAddress();
			
			// Get AddressId
			String addressId = UUID.randomUUID().toString();
			
			// Get UserId
			String userId = request.getAttribute("userId").toString();
			
			stmt = (PreparedStatement) conn.prepareStatement(Constants.INSERT_DELIVERY_ADDRESS);
			stmt.setString(1, addressId);
			stmt.setString(2, address.getAddressLine1());
			stmt.setString(3, address.getAddressLine2());
			stmt.setString(4, address.getCity());
			stmt.setString(5, address.getState());
			stmt.setString(6, address.getZipcode());
			stmt.setString(7, userId);
			
			stmt.executeUpdate();
			
			
			// Insert Order Details
//			String orderId = UUID.randomUUID().toString();
			String orderId = order.getId();
			
			stmt = (PreparedStatement) conn.prepareStatement(Constants.INSERT_ORDER);
			stmt.setString(1, orderId);
			stmt.setString(2, order.getOrderDate());
			stmt.setString(3, order.getPayment().getTransactionId());
			stmt.setString(4, order.getDeliveryOptions().getDeliveryDate());
			stmt.setDouble(5, order.getDeliveryOptions().getDeliveryType());
			stmt.setString(6, addressId);
			stmt.setDouble(7, order.getCart().getSubTotal());
			stmt.setDouble(8, order.getCart().getTotalPrice());
			stmt.setInt(9, order.getCart().getItemCount());
			stmt.setString(10, userId);
			
			stmt.executeUpdate();
			
			
			for (Item item: order.getCart().getItems()) {
				
				// Insert Order Items
				stmt = (PreparedStatement) conn.prepareStatement(Constants.INSERT_ORDER_ITEMS);
				stmt.setString(1, orderId);
				stmt.setString(2, item.getId());
				stmt.setInt(3, item.getItemCount());
				
				stmt.executeUpdate();
			}
			
			responseData.addProperty("message", "Order Placed Successfully!");
			
			
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseData.addProperty("message", "Error");
			responseData.addProperty("errorDetails", e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		} finally {
			try {
				stmt.close();
				conn.close();
				initContext.close();
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				responseData.addProperty("message", "Error");
				responseData.addProperty("errorDetails", e.getMessage());
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			
			
		}
		
		String responseDataJson = new Gson().toJson(responseData);
		response.getWriter().write(responseDataJson);
		response.getWriter().flush();
	}

}
