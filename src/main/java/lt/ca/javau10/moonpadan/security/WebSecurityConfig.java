package lt.ca.javau10.moonpadan.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lt.ca.javau10.moonpadan.services.jwt.AuthTokenFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService; // Service for loading user details from database
	
	@Autowired
	AuthenticationEntryPoint unauthorizedHandler;  // Custom entry point to handle unauthorized access
	
	@Bean
	AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider () {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider ();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	// Bean to encode passwords using bcrypt hashing algorithm
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// Configure CORS (Cross-Origin Resource Sharing) to allow requests from specific origins (e.g., localhost:3000)
		http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:3000"));
            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(List.of("*"));
            return configuration;
        }))
		// Disable CSRF protection since the app is stateless (JWTs are used instead)
		.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> 
		auth.requestMatchers("/api/auth/**").permitAll()
		.requestMatchers("/api/cart/*").permitAll()
		.requestMatchers("/api/products/*").permitAll()
		.requestMatchers("/api/products").permitAll()
		.requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
		.requestMatchers("/api/test/*").authenticated()
		.anyRequest().permitAll()       //.authenticated()
		
       
        )
		.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authenticationProvider(authenticationProvider());
	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
}
