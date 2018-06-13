package com.april.testproject;

import com.april.testproject.dto.IdeaDto;
import com.april.testproject.dto.UserDto;
import com.april.testproject.entity.Idea;
import com.april.testproject.entity.User;
import com.april.testproject.repository.IdeaRepository;
import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Component
@RestController
@RequestMapping("/api/v1")
public class ApiController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IdeaRepository ideaRepository;

	@PostMapping(value = "user", consumes = "application/json")
	public Object createUser(@Valid @RequestBody UserDto userDto) {
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setEmail(userDto.getEmail());
		user.setRole(userDto.getRole());
		user.setCountry(userDto.getCountry());
		user.setPassword(userDto.getPassword());
		user.setTags(userDto.getTags());
		user.setLastName(userDto.getLastName());
		user.setAvatarPicture(userDto.getAvatarPicture());
		user.setAboutUser(userDto.getAboutUser());
		user.setAboutCompany(userDto.getAboutCompany());
		user.setCountry(userDto.getCountry());
		user.setCity(userDto.getCity());
		userRepository.save(user);
		return user;
	}

	@PostMapping(value = "idea", consumes = "application/json")
	public Object createIdea(@RequestBody IdeaDto ideaDto) {
		Idea idea = new Idea();
		idea.setShortDescription(ideaDto.getShortDescription());
		idea.setStatus(ideaDto.getStatus());
		idea.setUserId(ideaDto.getUserId());
		ideaRepository.save(idea);
		return idea;
	}

	@GetMapping(value = "user/{id}", consumes = "application/json")
	public Object getUserById(@PathVariable(value = "id") Long userId) {
		return userRepository.findOne(userId);
	}

	@GetMapping(value = "users", consumes = "application/json")
	public Object getUsers() {
		return userRepository.findAll();
	}

	@GetMapping(value = "idea/{id}", consumes = "application/json")
	public Object getIdeaById(@PathVariable(value = "id") Long ideaId) {
		return ideaRepository.findOne(ideaId);
	}

	@GetMapping(value = "ideas", consumes = "application/json")
	public Object getIdeas() {
		return ideaRepository.findAll();
	}

	@GetMapping(value = "getIdeasByUserId/{userId}", consumes = "application/json")
	public List<Idea> getIdeasByUserId(@PathVariable("userId") String userId) {
		return ideaRepository.findByUserId(userId);
	}

	@PutMapping(value = "user", consumes = "application/json")
	public Object updateUser(@Valid @RequestBody UserDto userDto) {
		Long id = userDto.getId();
		User user = userRepository.findOne(id);
		if (userDto.getCountry() != null) user.setCountry(userDto.getCountry());
		if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
		if (userDto.getFirstName() != null) user.setFirstName(userDto.getFirstName());
		if (userDto.getRole() != null) user.setRole(userDto.getRole());
		userRepository.save(user);
		return user;
	}

	@PutMapping(value = "idea", consumes = "application/json")
	public Object updateIdea(@Valid @RequestBody IdeaDto ideaDto) {
		Long id = ideaDto.getId();
		Idea idea = ideaRepository.findOne(id);
		if (ideaDto.getShortDescription() != null) idea.setShortDescription(ideaDto.getShortDescription());
		if (ideaDto.getStatus() != null) idea.setStatus(ideaDto.getStatus());
		if (ideaDto.getUserId() != null) idea.setUserId(ideaDto.getUserId());
		ideaRepository.save(idea);
		return idea;
	}

	@DeleteMapping(value = "user/{id}")
	public Object deleteUser(@PathVariable("id") Long id) {
		userRepository.delete(id);
		return id;
	}

	@DeleteMapping(value = "idea/{id}")
	public Object deleteIdea(@PathVariable("id") Long id) {
		ideaRepository.delete(id);
		return id;
	}

	@GetMapping(value = "getUserByName/{name}")
	public List<User> getUserByName(@PathVariable("name") String name) {
		return userRepository.findByFirstNameContaining(name);
	}
}
