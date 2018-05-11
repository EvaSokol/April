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

import javax.validation.Valid;
import java.util.List;

@Component
@RestController
@RequestMapping("/api/1")
public class ApiController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdeasRepository ideasRepository;

//    @GetMapping("test")
//    public Object test(){
//        return new HashMap<>();
//    }

    @PostMapping(value = "createUser", consumes = "application/json")
    public Object createUser(@Valid @RequestBody UserDto userDto){

        User user = new User();
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());
        user.setCountry(userDto.getCountry());
        user.setPassword(userDto.getPassword());
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

    @GetMapping(value = "getUser/{id}", consumes = "application/json")
    public Object getUserById(@PathVariable(value = "id") Long userId){
        User user = userRepository.findOne(userId);
        return user;
    }

    @GetMapping(value = "getUsers", consumes = "application/json")
    public Object getUsers(){
        return userRepository.findAll();
    }

    @GetMapping(value = "getIdea/{id}", consumes = "application/json")
    public Object getIdeaById(@PathVariable(value = "id") Long ideaId){
        Idea idea = ideasRepository.findOne(ideaId);
        return idea;
    }

    @GetMapping(value = "getIdeas", consumes = "application/json")
    public Object getIdeas(){
        return ideasRepository.findAll();
    }

    @GetMapping(value = "getIdeasByUserId/{userId}", consumes = "application/json")
    public List<Idea> getIdeasByUserId(@PathVariable("userId") String userId){
        return ideasRepository.findByUserId(userId);
    }

    @PutMapping(value = "updateUser", consumes = "application/json")
    public Object updateUser(@Valid @RequestBody UserDto userDto){
        Long id = Long.valueOf(userDto.getId());
        User user = userRepository.findOne(id);
        if (userDto.getCountry() != null) user.setCountry(userDto.getCountry());
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getRole() != null) user.setRole(userDto.getRole());
        userRepository.save(user);
        return user;
    }

}
