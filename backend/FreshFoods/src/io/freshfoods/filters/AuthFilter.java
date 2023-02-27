package io.freshfoods.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.freshfoods.constants.Constants;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter(filterName = "AuthFilter",urlPatterns = {"/api/*"})
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
//		HttpServletRequest req = (HttpServletRequest) request;
//        System.out.println("Auth Header: " + req.getHeader("Authorization"));
//        
//        String authHeader =  req.getHeader("Authorization");
//        HttpServletResponse resp = (HttpServletResponse) response;
//        
//        if (authHeader == null) {
//        	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Request does not have Authorization Header");
//        } else {
//        	
//        	String jwtToken = authHeader.substring(7);
//        	System.out.println("jwt: " + jwtToken);
//        	
//        	try {
//        	    Algorithm algorithm = Algorithm.HMAC256(Constants.JWTSecret);
//        	    JWTVerifier verifier = JWT.require(algorithm)
//        	        .withIssuer(Constants.JWTIssuer)
//        	        .build();
//        	    DecodedJWT jwt = verifier.verify(jwtToken);
//        	    chain.doFilter(request, response);
//        	} catch (JWTVerificationException exception){
//        	    //Invalid signature/claims
//        		resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization Header");
//        	}
//        }

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
