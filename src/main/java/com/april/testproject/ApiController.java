package com.april.testproject;

import com.april.testproject.config.AppUserDetailsService;
import com.april.testproject.dto.LikeDto;
import com.april.testproject.entity.*;
import com.april.testproject.repository.IdeaRepository;
import com.april.testproject.repository.LikeRepository;
import com.april.testproject.repository.TagRepository;
import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/api/v1")
public class ApiController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IdeaRepository ideaRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private LikeRepository likeRepository;

	@GetMapping(value = "login", consumes = "application/json")
	public Object login() {
		return AppUserDetailsService.getUser();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(value = "like", consumes = "application/json")
	public Object like(@RequestBody LikeDto likeDto) {
		User user = AppUserDetailsService.getUser();
		Long ideaId = likeDto.getIdeaId();
		Idea idea = ideaRepository.findOne(ideaId);
		List<Like> likes = likeRepository.findLike(user.getId(), ideaId);
		if (likes.size() != 0) return null;
		Like like = new Like();
		like.setIdeaId(ideaId);
		like.setUserId(user.getId());
		likeRepository.save(like);
		int rate = likeRepository.getLikesOfIdea(ideaId);
		idea.setRate(rate);
		ideaRepository.save(idea);
		return like;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(value = "disLike", consumes = "application/json")
	public Object disLike(@RequestBody LikeDto likeDto) {
		User user = AppUserDetailsService.getUser();
		Long ideaId = likeDto.getIdeaId();
		Idea idea = ideaRepository.findOne(ideaId);
		Like like = likeRepository.findLike(user.getId(), ideaId).get(0);
		Long likeId = like.getId();
		likeRepository.delete(like);
		int rate = likeRepository.getLikesOfIdea(ideaId);
		idea.setRate(rate);
		ideaRepository.save(idea);
		return likeId;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(value = "isLiked", consumes = "application/json")
	public Boolean isLiked(@RequestBody LikeDto likeDto) {
		User user = AppUserDetailsService.getUser();
		return likeRepository.findLike(user.getId(), likeDto.getIdeaId()).size() != 0;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "like/{id}", consumes = "application/json")
	public Object getLikesOfIdea(@PathVariable(value = "id") Long ideaId) {
		return likeRepository.getLikesOfIdea(ideaId);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "tags", consumes = "application/json")
	public Object getTags() {
		return tagRepository.findAll();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getTagsByIdeaId/{ideaId}", consumes = "application/json")
	public List<String> getTagsByIdeaId(@PathVariable("ideaId") String ideaId) {
		return ideaRepository.getOne(Long.valueOf(ideaId)).getTags();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "tag/{id}", consumes = "application/json")
	public Object getTagById(@PathVariable(value = "id") Long id) {
		return tagRepository.findOne(id);
	}
}
