package com.april.testproject;

import com.april.testproject.dto.UserDto;
import com.april.testproject.entity.User;
import com.april.testproject.entity.UserRoleEnum;
import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.april.testproject.utils.ApiUtils.encryptPassword;

/**
 * Created by Eva Sokolyanskaya on 20/06/2018.
 */
@Component
@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "registration", consumes = "application/json")
	public Object createUser(@Valid @RequestBody UserDto userDto) {
		User user = new User();
		if (userRepository.findByEmailContaining(userDto.getEmail()).size() != 0) return "User already exists";
		user.setEmail(userDto.getEmail());
		user.setPassword(encryptPassword(userDto.getPassword()));
		user.setRole(UserRoleEnum.ROLE_USER.toString());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setAvatarPicture(userDto.getAvatarPicture());
		user.setAboutUser(userDto.getAboutUser());
		user.setAboutCompany(userDto.getAboutCompany());
		user.setCountry(userDto.getCountry());
		user.setCity(userDto.getCity());
		user.setRegDate(new Date());
		userRepository.save(user);
		return user;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PutMapping(value = "user", consumes = "application/json")
	public Object updateUser(@Valid @RequestBody UserDto userDto) {
		Long id = userDto.getId();
		User user = userRepository.findOne(id);
		if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
		if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());
		if (userDto.getRole() != null) user.setRole(userDto.getRole());
		if (userDto.getFirstName() != null) user.setFirstName(userDto.getFirstName());
		if (userDto.getLastName() != null) user.setLastName(userDto.getLastName());
		if (userDto.getAvatarPicture() != null) user.setAvatarPicture(userDto.getAvatarPicture());
		if (userDto.getAboutUser() != null) user.setAboutUser(userDto.getAboutUser());
		if (userDto.getAboutCompany() != null) user.setAboutCompany(userDto.getAboutCompany());
		if (userDto.getCountry() != null) user.setCountry(userDto.getCountry());
		if (userDto.getCity() != null) user.setCity(userDto.getCity());
		if (userDto.getRegDate() != null) user.setRegDate(userDto.getRegDate());
		userRepository.save(user);
		return user;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@DeleteMapping(value = "user/{id}")
	public Object deleteUser(@PathVariable("id") Long id) {
		userRepository.delete(id);
		return id;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getUserByName/{name}")
	public List<User> getUserByName(@PathVariable("name") String name) {
		return userRepository.findByFirstNameContaining(name);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getUserByEmail/{email}")
	public User getUserByEmail(@PathVariable("email") String email) {
		return userRepository.findByEmailContaining(email).get(0);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "user/{id}", consumes = "application/json")
	public Object getUserById(@PathVariable(value = "id") Long userId) {
		return userRepository.findOne(userId);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "users", consumes = "application/json")
	public Object getUsers() {
		return userRepository.findAll();
	}
}
