package lt.ca.javau10.moonpadan.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lt.ca.javau10.moonpadan.entities.ERole;
import lt.ca.javau10.moonpadan.entities.Role;
import lt.ca.javau10.moonpadan.entities.UserDto;
import lt.ca.javau10.moonpadan.entities.UserEntity;
import lt.ca.javau10.moonpadan.repositories.RoleRepository;
import lt.ca.javau10.moonpadan.repositories.UserRepository;
import lt.ca.javau10.moonpadan.request.LoginRequest;
import lt.ca.javau10.moonpadan.request.SignupRequest;
import lt.ca.javau10.moonpadan.responses.JwtResponse;
import lt.ca.javau10.moonpadan.responses.MessageResponse;
import lt.ca.javau10.moonpadan.security.JwtUtils;

@Service
public class AuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	AuthenticationManager autheticationManager;
	
	UserRepository userRepository;
	
	RoleRepository roleRepository;
	
	PasswordEncoder encoder;
	
	JwtUtils jwtUtils;
	
	public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
		super();
		this.autheticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		
		Authentication authentication = autheticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDto userDetails = (UserDto) authentication.getPrincipal();
		 logger.info("Before: " + userDetails.toString());
		 List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		 return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
			}
			
		public MessageResponse registerUser(SignupRequest signUpRequest) {
			
			checkUserExists(signUpRequest);
			
			UserEntity user = createNewUser(signUpRequest);
	        Set<Role> roles = getInitialRoles(signUpRequest);

	        user.setRoles(roles);
	       
	        logger.info("Before: " + user.toString());
	        user = userRepository.save(user);
	        logger.info("After: " + user.toString());
	        
	        return new MessageResponse("User registered successfully!");
		}
		
	        private UserEntity createNewUser(SignupRequest signUpRequest) {
	    		UserEntity user = new UserEntity(
	    				signUpRequest.getUsername(), 
	    				encoder.encode(signUpRequest.getPassword()),
	                    signUpRequest.getEmail());
	    		logger.info(user.toString());
	    		return user;
	    	}
	    	private Set<Role> getInitialRoles(SignupRequest signUpRequest) {
	    		Set<String> strRoles = signUpRequest.getRole();
	            Set<Role> roles = new HashSet<>();

	            if (strRoles == null || strRoles.isEmpty()) {
	                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
	                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                roles.add(userRole);
	            } else {
	                for (String role : strRoles) {
	                    Role resolvedRole = roleRepository.findByName(ERole.valueOf("ROLE_" + role.toUpperCase()))
	                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                    roles.add(resolvedRole);
	                }
	            }
	    		return roles;
	    	}	
	    private void checkUserExists(SignupRequest signUpRequest) {
	    			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	    	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
	    	        }

	    	        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	    	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Email is already in use!");
	    	        }
	}
}

