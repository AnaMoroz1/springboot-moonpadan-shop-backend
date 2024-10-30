package lt.ca.javau10.moonpadan.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lt.ca.javau10.moonpadan.entities.UserDto;
import lt.ca.javau10.moonpadan.services.UserService;



@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Autowired
	UserService userService;
	
	@Value("${javau10.jwtSecret}")
	private String jwtSecret;
	
	@Value("${javau10.jwtExpirationMs}")
	  private int jwtExpirationMs;

	public void setJwtSecret(String key) {
		jwtSecret = key;
	}

	public void setJwtExpirationMs(int ms) {
		jwtExpirationMs = ms;
	}
	public String generateJwtToken(Authentication authentication) {
		
		UserDto userPrincipal = (UserDto) authentication.getPrincipal();
		
		return Jwts.builder()
		        .subject((userPrincipal.getUsername()))
		        .issuedAt(new Date())
		        .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
		        .signWith(key())
		        .compact();
	}

	private Key toKey(String secret) {
		
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		
	}
	 private Key key() {
		    return toKey(jwtSecret);
		  }

		  public String getUserNameFromJwtToken(String token) {
			  
			  return Jwts
					  .parser()
					  .verifyWith((SecretKey) key())
					  .build()
		              .parseSignedClaims(token)
		              .getPayload()
		              .getSubject();
		  }
		  public boolean validateJwtToken(String authToken) {
			    try {
			      Jwts.parser().verifyWith((SecretKey)key()).build().parse(authToken);
			      return true;
			    } catch (MalformedJwtException e) {
			      logger.error("Invalid JWT token: {}", e.getMessage());
			    } catch (ExpiredJwtException e) {
			      logger.error("JWT token is expired: {}", e.getMessage());
			    } catch (UnsupportedJwtException e) {
			      logger.error("JWT token is unsupported: {}", e.getMessage());
			    } catch (IllegalArgumentException e) {
			      logger.error("JWT claims string is empty: {}", e.getMessage());
			    }
			    return false;
		  }
		  public Authentication getAuthentication(String token) {
			    logger.trace("Suveike metodas getAuthentication");

			    String username = getUserNameFromJwtToken(token);
			    UserDetails userDetails = userService.loadUserByUsername(username);
			    logger.debug("UserDetails loaded: " + userDetails.getUsername() + ", Roles: " + userDetails.getAuthorities());

			    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		  }
		}
