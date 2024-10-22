package lt.ca.javau10.moonpadan.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lt.ca.javau10.moonpadan.request.LoginRequest;
import lt.ca.javau10.moonpadan.request.SignupRequest;
import lt.ca.javau10.moonpadan.responses.JwtResponse;
import lt.ca.javau10.moonpadan.responses.MessageResponse;
import lt.ca.javau10.moonpadan.services.AuthService;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	AuthService authService;
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		logger.info("Trying to signup \n" + signUpRequest);
		
        try {
            MessageResponse response = authService.registerUser(signUpRequest);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
        	
            return ResponseEntity.status(e.getStatusCode())
            		.body(new MessageResponse(e.getReason()));
        }
     }
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser (@RequestBody LoginRequest loginRequest){
		logger.info("login request receved");
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);		
	}
	
    
	
}
	
