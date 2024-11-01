package lt.ca.javau10.moonpadan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.moonpadan.entities.UserDto;
import lt.ca.javau10.moonpadan.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		UserDto createdUser = userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){	
		Optional<UserDto> userInBox = userService.getUserById(id);
		return userInBox
				.map( ResponseEntity::ok )
				.orElseGet( () -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(
										@PathVariable Long id, 
										@RequestBody UserDto userDto){
		Optional<UserDto> userInBox = userService.updateUser(id, userDto);
		
		return userInBox
				.map( ResponseEntity::ok )
				.orElseGet( () -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
