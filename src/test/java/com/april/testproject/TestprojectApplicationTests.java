package com.april.testproject;

import com.april.testproject.entity.Idea;
import com.april.testproject.entity.User;
import com.april.testproject.repository.IdeasRepository;
import com.april.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

@SpringBootTest
public class TestprojectApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	IdeasRepository ideasRepository;

	private int random;
	private Long userId;
	private Long ideaId;
	private int numbetOfUsers;
	private int numbetOfIdeas;


	@BeforeClass
	public void init(){
		random = (int) (Math.random() * 1000);
		numbetOfUsers = userRepository.findAll().size();
		numbetOfIdeas = ideasRepository.findAll().size();
	}

	@Test
	public void createUser() {
		User user = new User();
		user.setName("TestUser" + random);
		user.setCountry("SomeCountry" + random);
		user.setRole("user");
		userId = userRepository.save(user).getId();
		user.print();
		assertEquals(userRepository.findAll().size(), numbetOfUsers + 1);
	}

	@Test(dependsOnMethods = "createUser")
	public void getAllUsers(){
		List<User> users =  userRepository.findAll();
		for (User us : users){
			us.print();
		}
		assertTrue(users.size()>0);
	}

	@Test(dependsOnMethods = "createUser")
	public void getUserById(){
		User user = userRepository.findOne(userId);
		user.print();
		assertTrue(user.getCountry().contains(String.valueOf(random)));
	}

	@Test(dependsOnMethods = "createUser")
	public void createIdea(){
		Idea idea = new Idea();
		idea.setShort_description("ShortDescription" + random);
		idea.setStatus("new");
		idea.setUserId(userId.toString());
		ideaId = ideasRepository.save(idea).getId();
		idea.print();
		assertEquals(ideasRepository.findAll().size(), numbetOfIdeas + 1);
	}

	@Test(dependsOnMethods = "createIdea")
	public void getIdeaById(){
		Idea idea = ideasRepository.findOne(ideaId);
		idea.print();
		assertTrue(idea.getShort_description().contains(String.valueOf(random)));
	}

	@Test(dependsOnMethods = "createIdea")
	public void getAllIdeas(){
		List<Idea> ideas = ideasRepository.findAll();
		for (Idea idea : ideas){
			idea.print();
		}
		assertTrue(ideas.size() > 0);
	}

	@Test(dependsOnMethods = "createIdea")
	public void getIdeasByUserId(){
		List<Idea> ideas = ideasRepository.findByUserId(userId.toString());
		for (Idea idea : ideas){
			idea.print();
		}
	}

}
