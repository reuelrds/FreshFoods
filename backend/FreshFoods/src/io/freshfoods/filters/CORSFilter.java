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

/**
 * Servlet Filter implementation class CORSFilter
 */
@WebFilter(urlPatterns= {"/*"}, asyncSupported = true)
public class CORSFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CORSFilter() {
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
		
		HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("CORSFilter HTTP Request: " + req.getMethod());
        System.out.println("CORSFilter HTTP Origin: " + req.getHeader("origin"));
        String clientOrigin = req.getHeader("origin");
 
        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", clientOrigin);
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Content-Type");
 
        HttpServletResponse resp = (HttpServletResponse) response;
 
        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (req.getMethod().equals("OPTIONS")) {
        	
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

		// pass the request along the filter chain
		chain.doFilter(req, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}