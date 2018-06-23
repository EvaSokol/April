package com.april.testproject;

import com.april.testproject.config.AppUserDetailsService;
import com.april.testproject.dto.IdeaDto;
import com.april.testproject.entity.Idea;
import com.april.testproject.entity.Tag;
import com.april.testproject.entity.User;
import com.april.testproject.repository.IdeaRepository;
import com.april.testproject.repository.LikeRepository;
import com.april.testproject.repository.TagRepository;
import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Eva Sokolyanskaya on 20/06/2018.
 */
@Component
@RestController
@RequestMapping("/api/v1")
public class IdeaController {

	@Autowired
	private IdeaRepository ideaRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private UserRepository userRepository;

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(value = "idea", consumes = "application/json")
	public Object createIdea(@RequestBody IdeaDto ideaDto) {
		Idea idea = new Idea();
		idea.setStatus(ideaDto.getStatus());
		idea.setUserId(AppUserDetailsService.getUser().getId().toString());
		idea.setHeader(ideaDto.getHeader());
		idea.setMainPicture(ideaDto.getMainPicture());
		idea.setShortDescription(ideaDto.getShortDescription());
		idea.setFullDescription(ideaDto.getFullDescription());
		idea.setPictureList(ideaDto.getPictureList());
		idea.setCreationDate(new Date());
		idea.setPrice(ideaDto.getPrice());
		idea.setRate(0);
		Set<Tag> tags = getTags(ideaDto.getTags());
		idea.setTags(tags);
		ideaRepository.save(idea);
		return idea;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PutMapping(value = "idea", consumes = "application/json")
	public Object updateIdea(@Valid @RequestBody IdeaDto ideaDto) {
		Long id = ideaDto.getId();
		Idea idea = ideaRepository.findOne(id);
		if (ideaDto.getStatus() != null) idea.setStatus(ideaDto.getStatus());
		if (ideaDto.getUserId() != null) idea.setUserId(ideaDto.getUserId());
		if (ideaDto.getHeader() != null) idea.setHeader(ideaDto.getHeader());
		if (ideaDto.getMainPicture() != null) idea.setMainPicture(ideaDto.getMainPicture());
		if (ideaDto.getShortDescription() != null) idea.setShortDescription(ideaDto.getShortDescription());
		if (ideaDto.getFullDescription() != null) idea.setFullDescription(ideaDto.getFullDescription());
		if (ideaDto.getPictureList() != null) idea.setPictureList(ideaDto.getPictureList());
		if (ideaDto.getPrice() != null) idea.setPrice(ideaDto.getPrice());
		if (ideaDto.getTags() != null) {
			Set<Tag> tags = getTags(ideaDto.getTags());
			idea.setTags(tags);
		}
		int rate = likeRepository.getLikesOfIdea(id);
		idea.setRate(rate);
		ideaRepository.save(idea);
		return idea;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@DeleteMapping(value = "idea/{id}")
	public Object deleteIdea(@PathVariable("id") Long id) {
		ideaRepository.delete(id);
		return id;
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "idea/{id}", consumes = "application/json")
	public Object getIdeaById(@PathVariable(value = "id") Long ideaId) {
		return ideaRepository.findOne(ideaId);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getIdeasPageByDate/{pageNumber}", consumes = "application/json")
	public Object getIdeasPageByDate(@PathVariable(value = "pageNumber") int pageNumber) {
		Pageable topTen = new PageRequest(pageNumber, 10);
		return ideaRepository.getIdeasPageByDate(topTen);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getNumberAllIdeas", consumes = "application/json")
	public Object getNumberAllIdeas() {
		return ideaRepository.getNumberAllIdeas();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "ideas", consumes = "application/json")
	public Object getIdeas() {
		return ideaRepository.findAll();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getIdeasByUserId/{userId}", consumes = "application/json")
	public List<Idea> getIdeasByUserId(@PathVariable("userId") String userId) {
		return ideaRepository.findByUserId(userId);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getUserByIdeaId/{ideaId}", consumes = "application/json")
	public User getUserByIdeaId(@PathVariable("ideaId") Long ideaId) {
		Long userId = Long.parseLong(ideaRepository.findOne(ideaId).getUserId());
		return userRepository.findOne(userId);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getIdeasByHeader/{word}", consumes = "application/json")
	public List<Idea> getIdeasByHeader(@PathVariable("word") String word) {
		return ideaRepository.findInHeader(word);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getIdeasByTag/{tagId}", consumes = "application/json")
	public List<BigInteger> getIdeasByTag(@PathVariable("tagId") Long tagId) {
		return ideaRepository.getIdeasByTag(tagId);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getIdeasSortedByLikes", consumes = "application/json")
	public List<Map<Integer, Long>> getIdeasSortedByLikes() {
		return likeRepository.getIdeasSortedByLikes();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping(value = "getFiveIdeasSortedByLikes", consumes = "application/json")
	public List getFiveIdeasSortedByLikes() {
		return likeRepository.getIdeasSortedByLikes().subList(0,4);
	}

	private Set<Tag> getTags(String tagString) {
		if (tagString == null) return null;
		List strings = Arrays.asList(tagString.split(","));
		Set<String> tagNameSet = new HashSet<>();
		if (strings.size() != 0) tagNameSet.addAll(strings);
		Set<Tag> tags = new HashSet<>();
		for (String tagName : tagNameSet){
			tags.add(tagRepository.findByTagName(tagName).get(0));
		}
		return tags;
	}

//	List<Long> getIdeasSortedByLikesMethod(){
//		List<Long> likes = likeRepository.getLikedIdeas();
//		Map<Long, Integer> sortedIdeasMap = new TreeMap<>();
//		for (Long ideaId : likes){
//			sortedIdeasMap.putIfAbsent(ideaId, likeRepository.getLikesOfIdea(ideaId));
//		}
//		List<Long> sortedIdeasList = new ArrayList<>();
//		for (Map.Entry<Long,Integer> entry : sortedIdeasMap.entrySet()) {
//			sortedIdeasList.add(entry.getKey());
//		}
//		return sortedIdeasList;
//	}

	// Doesn't work:
//	Map<Long, Integer> newMap = new TreeMap<>();
//	List<Map<Integer, Long>> list =likeRepository.getIdeasSortedByLikes();
//for (Map<Integer, Long> oneMap : list){
//		Integer rate = 0;
//		Long ideaId = null;
//		for (Map.Entry entry : oneMap.entrySet()) {
//			rate = Integer.parseInt(entry.getKey().toString());
//			ideaId = Long.parseLong(entry.getValue().toString());
//		}
//		newMap.put(ideaId, rate);
//	}

}
