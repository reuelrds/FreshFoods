package io.freshfoods.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.freshfoods.constants.Constants;
import io.freshfoods.utils.Utils;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet(name="SignUpServlet", urlPatterns="/auth/signup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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

		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		String userId = UUID.randomUUID().toString();
		
		
		String password = data.get("password").getAsString();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		
//		System.out.println(id.length());
		
		
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		JsonObject responseData = new JsonObject();
		
		try {
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/FreshFoods");
			conn = ds.getConnection();
			
			stmt = (PreparedStatement) conn.prepareStatement(Constants.INSERT_USER);
			stmt.setString(1, userId);
			stmt.setString(2, data.get("name").getAsString());
			stmt.setString(3, data.get("email").getAsString());
			stmt.setString(4, hashedPassword);
			
			int result = stmt.executeUpdate();
			
			System.out.println(result);
			
			String jwtToken = Utils.getJWTToken();

			
		    response.setHeader("Authorization", "Bearer " + jwtToken);
		    
		    responseData.addProperty("message", "Signup Successfull");
		    responseData.addProperty("userId", userId);
		    responseData.addProperty("jwtToken", jwtToken);
		    responseData.addProperty("expiresin", Constants.EXPIRATION_TIME);
		    
		    response.setStatus(HttpServletResponse.SC_CREATED);
		    
		    

			
		} catch (SQLException | NamingException e) {
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
		
		
//		JWTVerifier verifier = JWT.require(algorithm)
//		        .withIssuer("auth0")
//		        .build(); 
//		DecodedJWT jwt = verifier.verify(token);
		
		
//		response.getWriter().append(data.toString());
		
		
	    
		String responseDataJson = new Gson().toJson(responseData);
		response.getWriter().write(responseDataJson);
		response.getWriter().flush();
	    
	}

}
