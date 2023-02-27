package io.freshfoods.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.google.gson.JsonObject;

import io.freshfoods.constants.Constants;
import io.freshfoods.order.schema.Address;
import io.freshfoods.order.schema.Item;
import io.freshfoods.order.schema.Order;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/api/placeOrder")
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			String addressId = UUID.randomUUID().toString();
			
			stmt = (PreparedStatement) conn.prepareStatement(Constants.INSERT_DELIVERY_ADDRESS);
			stmt.setString(1, addressId);
			stmt.setString(2, address.getAddressLine1());
			stmt.setString(3, address.getAddressLine2());
			stmt.setString(4, address.getCity());
			stmt.setString(5, address.getState());
			stmt.setString(6, address.getZipcode());
			
			stmt.executeUpdate();
			
			
			// Insert Order Details
			String orderId = UUID.randomUUID().toString();
			
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
