package lt.ca.javau10.moonpadan.services.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lt.ca.javau10.moonpadan.security.JwtUtils;

public class AuthTokenFilter extends OncePerRequestFilter {

	 @Autowired
	 private JwtUtils jwtUtils;
	 
	 @Autowired
	 UserDetailsService userService;
	 
	 private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	 @Override
	 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	         throws ServletException, IOException {
		// Parse the JWT from the request
	     String jwt = parseJwt(request);
	     
	  // Check if the JWT is valid
	     if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
	    	// If valid, retrieve the authentication object from the JWT
	         Authentication authentication = jwtUtils.getAuthentication(jwt);
	      // If authentication is not null, set it in the SecurityContext
	         if (authentication != null) {
	             SecurityContextHolder.getContext().setAuthentication(authentication);
	             logger.debug("Authentication set: " + SecurityContextHolder.getContext().getAuthentication());
	     } else {
	        	 logger.warn("Authentication is null, not setting SecurityContext");
	         }
	     } else {
	         logger.warn("JWT is either null or invalid");
	     }
	  // Proceed with the filter chain
	     filterChain.doFilter(request, response);
	 }
	 
	
	private String parseJwt(HttpServletRequest request) {
	    String headerAuth = request.getHeader("Authorization");

	 // Check if the header is present and starts with "Bearer "
	    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	    	// Extract and return the JWT, removing the "Bearer " prefix
	    	return headerAuth.substring(7);
	    }
	 // Return null if the JWT is not present or improperly formatted
	    return null;
	}

}
