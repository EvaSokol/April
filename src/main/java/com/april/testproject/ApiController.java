package com.april.testproject;

import com.april.testproject.dto.UserDto;
import com.april.testproject.entity.User;
import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Component
@RestController
@RequestMapping("/api/1")
public class ApiController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("test")
    public Object test(){
        return new HashMap<>();
    }

    @PostMapping(value = "createUser", consumes = "application/json")
    public Object createUser(@RequestBody UserDto userDto){

        User user = new User();
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return user;
    }

}
