package com.april.testproject;

import com.april.testproject.entity.Idea;
import com.april.testproject.entity.User;
import com.april.testproject.entity.UserRoleEnum;
import com.april.testproject.repository.IdeasRepository;
import com.april.testproject.repository.UserRepository;
import com.jayway.restassured.path.json.JsonPath;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
    private String password = "password123";

	@BeforeClass
	public void init(){
		random = (int) (Math.random() * 1000);
		numbetOfUsers = userRepository.findAll().size();
		numbetOfIdeas = ideasRepository.findAll().size();
	}

	@Test(enabled = false)
	public void createUserFast() {
		User user = new User();
		user.setName("TestUser" + random);
		user.setCountry("Some Country" + random);
		user.setRole(UserRoleEnum.USER);
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

	@Test(dependsOnMethods = "createUser",enabled = false)
	public void createIdeaFast(){
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

	//TODO: make autostart application for test
	@Test
	public void createUser() throws Exception {
		String country = "Some Country" + random;
		String name = "Virender" + random;
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", name);
		requestParams.put("country", country);
		requestParams.put("role", UserRoleEnum.USER);
		requestParams.put("password", password);

		String uri = "http://localhost:8080/api/1/createUser";
		JsonPath response = RestTests.post(uri, requestParams);
		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(Long.valueOf(userId));
		assertTrue(user.getName().equalsIgnoreCase(String.valueOf(name)));
		assertTrue(user.getCountry().equalsIgnoreCase(String.valueOf(country)));
		}

	@Test(dependsOnMethods = "createUser")
	public void createIdea() throws JSONException {
		String shortDescription = "ShortDescription" + random;
		String status = "new";
		JSONObject requestParams = new JSONObject();
		requestParams.put("short_description", shortDescription);
		requestParams.put("status", status);
		requestParams.put("userId", userId.toString());

		String uri = "http://localhost:8080/api/1/createIdea";
		ideaId = Long.valueOf(RestTests.post(uri, requestParams).get("id").toString());
		Idea idea = ideasRepository.findOne(Long.valueOf(ideaId));
		assertTrue(idea.getShort_description().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertTrue(idea.getUserId().equals(String.valueOf(userId)));
		}
}
