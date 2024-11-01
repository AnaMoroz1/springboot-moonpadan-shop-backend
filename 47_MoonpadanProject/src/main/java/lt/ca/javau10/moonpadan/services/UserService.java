package lt.ca.javau10.moonpadan.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lt.ca.javau10.moonpadan.entities.UserDto;
import lt.ca.javau10.moonpadan.entities.UserEntity;
import lt.ca.javau10.moonpadan.repositories.UserRepository;
import lt.ca.javau10.moonpadan.utils.EntityMapper;


@Service
public class UserService implements UserDetailsService{
	//Field is declared final, 
	//which means it must be initialized once,
	private final UserRepository userRepository;
	private final EntityMapper entityMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	public UserService(UserRepository userRepository, EntityMapper entityMapper) {
		this.userRepository = userRepository;
		this.entityMapper = entityMapper;
	}
	//CRUD - Create, Read, Update, Delete
	
	public UserDto createUser(UserDto userDto) {
		UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);

		UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);		
		
		return entityMapper.toUserDto(userEntityAfterSave);		
	}
	public List<UserDto> getAllUsers(){
		List<UserEntity> users = userRepository.findAll();
		
		return users.stream()
				.map(entityMapper::toUserDto)
				.toList();		
	}
	public Optional<UserDto> getUserById(Long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		
		return user.map(entityMapper::toUserDto);
	}
	public Optional<UserDto> updateUser(Long id, UserDto userDto ){
		
		if( userRepository.existsById(id) ) {
			UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);
			userEntityBeforeSave.setId(id);
			
			UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);
			return Optional.of( entityMapper.toUserDto(userEntityAfterSave));
			
		} else {
			return Optional.empty();
		}
	}	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository
							.findByUsername(username)
							.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		
		logger.info("Loaded :"+user.toString());
		return entityMapper.toUserDto(user);
	}
	
}