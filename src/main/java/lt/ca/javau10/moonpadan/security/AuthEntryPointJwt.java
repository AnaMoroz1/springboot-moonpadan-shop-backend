package lt.ca.javau10.moonpadan.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
	// This code handles cases where the user is not authorized,
	// sending a response to the client with a detailed error message in JSON format 
	// and 401 Unauthorized.
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
		throws IOException, ServletException {
		
		// Log an error message when an unauthorized access attempt is detected
		logger.error("Unauthorized error: {}", authException.getMessage());
		
		// Set the response content type to JSON
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		// Set the HTTP status code to 401 (Unauthorized)
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		// Create a response body with key details: status, error type, message, and the requested path
		final Map<String, Object> body = new HashMap<>();
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);  // Add the HTTP status code to the body
	    body.put("error", "Unauthorized");   // Specify the error as "Unauthorized"
	    body.put("message", authException.getMessage());   // Include the authentication error message
	    body.put("path", request.getServletPath());   // Add the requested URL path

	    // Use ObjectMapper to write the response body as a JSON object to the output stream
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getOutputStream(), body);
	}
}
