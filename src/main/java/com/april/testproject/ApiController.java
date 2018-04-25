package com.april.testproject;

import com.april.testproject.dto.IdeaDto;
import com.april.testproject.dto.UserDto;
import com.april.testproject.entity.Idea;
import com.april.testproject.entity.User;
import com.april.testproject.repository.IdeasRepository;
import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;

@Component
@RestController
@RequestMapping("/api/1")
public class ApiController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdeasRepository ideasRepository;

    @GetMapping("test")
    public Object test(){
        return new HashMap<>();
    }

    @PostMapping(value = "createUser", consumes = "application/json")
    public Object createUser(@RequestBody UserDto userDto){

        User user = new User();
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());
        user.setCountry(userDto.getCountry());
        userRepository.save(user);
        return user;
    }

    @PostMapping(value = "createIdea", consumes = "application/json")
    public Object createIdea(@RequestBody IdeaDto ideaDto){
        Idea idea = new Idea();
        idea.setShort_description(ideaDto.getShort_description());
        idea.setStatus(ideaDto.getStatus());
        idea.setUserId(ideaDto.getUserId());
        ideasRepository.save(idea);
        return idea;
    }

    @GetMapping(value = "getUser/{id}")
    public Object getUserById(@PathVariable(value = "id") Long userId){
        User user = userRepository.findOne(userId);
        return user;
    }

    @GetMapping(value = "getIdeasOfUser/{id)")
    public Set<Idea> getIdeasOfUserById(@PathVariable(value = "id") Long userId){
        return userRepository.findOne(userId).getIdeaSet();
    }

    @GetMapping(value = "getUsers", consumes = "application/json")
    public Object getUsers(){
        return userRepository.findAll();
    }

}
