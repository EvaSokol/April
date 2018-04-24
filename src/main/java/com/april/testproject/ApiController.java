package com.april.testproject;

import com.april.testproject.dto.IdeaDto;
import com.april.testproject.dto.UserDto;
import com.april.testproject.entity.Idea;
import com.april.testproject.entity.User;
import com.april.testproject.repository.IdeasRepository;
import com.april.testproject.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Component
@RestController
@RequestMapping("/api/1")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

//TODO: Does not work for now - to repare
    @GetMapping(value = "getUser/{id}")
    public Object getUserById(@PathVariable(value = "id") Long userId){
        User user = userRepository.getOne(userId);
        return user;
    }

    @GetMapping(value = "getUsers", consumes = "application/json")
    public Object getUsers(){
        return userRepository.findAll();
    }

}
