package com.april.testproject;

import com.april.testproject.entity.User;
import com.april.testproject.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestprojectApplicationTests {

	@Autowired
	UserRepository userRepository;

//	@Test
//	public void contextLoads() {
//		User user = new User("Pit");
//		userRepository.save(user);
//	}

}
