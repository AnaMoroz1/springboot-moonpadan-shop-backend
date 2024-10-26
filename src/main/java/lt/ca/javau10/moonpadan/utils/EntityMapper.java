package lt.ca.javau10.moonpadan.utils;

import org.springframework.stereotype.Component;

import lt.ca.javau10.moonpadan.entities.UserDto;
import lt.ca.javau10.moonpadan.entities.UserEntity;

@Component
public class EntityMapper {
	
	// Method to convert a UserDto to a UserEntity
	public UserEntity toUserEntity(UserDto dto) {
		
		UserEntity entity = new UserEntity();
		// Set the properties of UserEntity from the UserDto
		entity.setId( dto.getId());
		entity.setUsername( dto.getUsername());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());	
		return entity;		
	}
	//Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
	
	public UserDto toUserDto(UserEntity entity) {
		return new UserDto(
				entity.getId(),
				entity.getUsername(),
				entity.getEmail(),
				entity.getRoles(),
				entity.getPassword());
	
	}
	
}