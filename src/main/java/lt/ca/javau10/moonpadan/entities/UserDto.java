package lt.ca.javau10.moonpadan.entities;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto implements UserDetails {
	
	private static final long serialVersionUID = -1L; //version identifier
													 //(-1L: any integer can be chosen)
	private Long id;
	private String username;
	private String email;
	
	public Long getId() {
		return id;
	}

	private Set<Role> roles;
	
	@JsonIgnore //must not be included in JSON mapping (serialization)
	private String password;

	public UserDto() {
		super();
	}

	
	public UserDto(Long id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}


	public UserDto(Long id, String username, String email, Set<Role> roles, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.password = password;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Collection <? extends GrantedAuthority> getAuthorities(){
		
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());		
	}
	
	@Override 
	public boolean isAccountNonExpired() {
		// Indicates whether the user's account has expired.
	    // Returning true means the account is valid and not expired.
		return true;
	}
	@Override 
	public boolean isCredentialsNonExpired() {
		// Indicates whether the user's credentials (password) have expired.
	    // Returning true means the credentials are still valid and have not expired.
		return true;
		}
	@Override 
	public boolean isEnabled() {
		// Indicates whether the user is enabled or disabled.
	    // Returning true means the user is enabled and allowed to log in.
		return true;
	}


	@Override
	public int hashCode() {
		// Generates a hash code based on the 'id' field.
	    // The 'Objects.hash()' method ensures a consistent hash code is generated.
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		//object check with self
		if (this == obj)
			return true;
		//checking for null and forge with each other
		if (obj == null || getClass() != obj.getClass())
			return false;
		//casting the object to the UserDto type
		UserDto other = (UserDto) obj;
		//comparing two id fields
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", email=" + email + ", roles=" + roles + ", password="
				+ password + "]";
	}
	
}
