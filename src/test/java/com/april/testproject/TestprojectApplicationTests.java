package com.april.testproject;

import com.april.testproject.entity.Idea;
import com.april.testproject.entity.User;
import com.april.testproject.entity.UserRoleEnum;
import com.april.testproject.repository.IdeaRepository;
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
import static com.april.testproject.RestTests.encryptPassword;
import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import com.april.testproject.RestTests.*;

@SpringBootTest
public class TestprojectApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
    IdeaRepository ideaRepository;

	private int random;
	private Long userId;
	private Long ideaId;
	private int numberOfUsers;
	private int numberOfIdeas;
    private String password = "password123";
    private String baseUrl = "http://localhost:8080/api/v1/";

	@BeforeClass
	public void init(){
		random = (int) (Math.random() * 1000);
		numberOfUsers = userRepository.findAll().size();
		numberOfIdeas = ideaRepository.findAll().size();
	}

	@Test(enabled = true)
	public void createUserFast() {
		User user = new User();
		user.setName("TestUser" + random);
		user.setCountry("Some Country" + random);
		user.setRole(UserRoleEnum.ROLE_USER.toString());
		user.setPassword(encryptPassword(password));
		userId = userRepository.save(user).getId();
		user.print();
		assertEquals("Amy", userRepository.findAll().get(0).getName() );
	}

	@Test(dependsOnMethods = "createUserFast")
	public void getAllUsersFast(){
		List<User> users =  userRepository.findAll();
		for (User us : users){
			us.print();
		}
		assertTrue(users.size()>0);
	}

	@Test(dependsOnMethods = "createUserFast")
	public void getUserByIdFast(){
		User user = userRepository.findOne(userId);
		user.print();
		assertTrue(user.getCountry().contains(String.valueOf(random)));
	}

	@Test(dependsOnMethods = "createUserFast",enabled = true)
	public void createIdeaFast(){
		Idea idea = new Idea();
		idea.setShortDescription("ShortDescription" + random);
		idea.setStatus("new");
		idea.setUserId(userId.toString());
		ideaId = ideaRepository.save(idea).getId();
		idea.print();
		assertEquals(ideaRepository.findAll().size(), numberOfIdeas + 1);
	}

	@Test(dependsOnMethods = "createIdeaFast")
	public void getAllIdeasFast(){
		List<Idea> ideas = ideaRepository.findAll();
		for (Idea idea : ideas){
			idea.print();
		}
		assertTrue(ideas.size() > 0);
	}

	@Test(dependsOnMethods = "createIdeaFast")
	public void getIdeasByUserIdFast(){
		List<Idea> ideas = ideaRepository.findByUserId(userId.toString());
		for (Idea idea : ideas){
			idea.print();
		}
	}

	@Test(enabled = false)
	public void getAllUsers(){
		String uri = baseUrl + "users";
		JsonPath response = RestTests.get(uri);

		List <String> res = response.get("name");
		String newName = res.get(0);

		assertEquals("Amy", newName);
	}

	@Test(dependsOnMethods = "createIdea", enabled = true)
	public void getIdeaById(){
		Idea idea = ideaRepository.findOne(ideaId);
		idea.print();
		assertTrue(idea.getShortDescription().contains(String.valueOf(random)));
	}

	//TODO: make autostart application for test
	@Test(dependsOnMethods = "getAllIdeasFast", enabled = true)
	public void createUser() throws Exception {
		String country = "Some Country" + random;
		String name = "Virender" + random;
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", name);
		requestParams.put("country", country);
		requestParams.put("role", UserRoleEnum.ROLE_ADMIN);
		requestParams.put("password", encryptPassword(password));

		String uri = baseUrl + "user";
		JsonPath response = RestTests.post(uri, requestParams);
		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(Long.valueOf(userId));
		assertTrue(user.getName().equalsIgnoreCase(String.valueOf(name)));
		assertTrue(user.getCountry().equalsIgnoreCase(String.valueOf(country)));
		}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void createIdea() throws JSONException {
		String shortDescription = "ShortDescription" + random;
		String status = "new";
		JSONObject requestParams = new JSONObject();
		requestParams.put("shortDescription", shortDescription);
		requestParams.put("status", status);
		requestParams.put("userId", userId.toString());

		String uri = baseUrl + "idea";
		ideaId = Long.valueOf(RestTests.post(uri, requestParams).get("id").toString());
		Idea idea = ideaRepository.findOne(Long.valueOf(ideaId));
		assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertTrue(idea.getUserId().equals(String.valueOf(userId)));
		}

	@Test(dependsOnMethods = "createUser", enabled = true)
    public void updateUser() throws JSONException {
        String country = "Some Another Country" + random;
        String name = "Virender Second" + random;
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", name);
        requestParams.put("country", country);
        requestParams.put("role", UserRoleEnum.ROLE_USER);
        requestParams.put("password", encryptPassword(password));
        requestParams.put("id", userId);

        String uri = baseUrl + "user";
        JsonPath response = RestTests.put(uri, requestParams);
        userId = Long.valueOf((response).get("id").toString());
        User user = userRepository.findOne(Long.valueOf(userId));
        assertTrue(user.getName().equalsIgnoreCase(String.valueOf(name)));
        assertTrue(user.getCountry().equalsIgnoreCase(String.valueOf(country)));
    }

    @Test(dependsOnMethods = "createIdea", enabled = true)
    public void updateIdea() throws JSONException {
        String shortDescription = "New ShortDescription" + random;
        String status = "approved";
        JSONObject requestParams = new JSONObject();
        requestParams.put("shortDescription", shortDescription);
        requestParams.put("status", status);
        requestParams.put("userId", userId.toString());
        requestParams.put("id", ideaId.toString());

        String uri = baseUrl + "idea";
        ideaId = Long.valueOf(RestTests.put(uri, requestParams).get("id").toString());
        Idea idea = ideaRepository.findOne(Long.valueOf(ideaId));
        assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
        assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
        assertTrue(idea.getUserId().equals(String.valueOf(userId)));
    }

    @Test(dependsOnMethods = "createUser", enabled = true)
    public void deleteUser() throws JSONException {
        String country = "Some Country" + random;
        String name = "Virender" + random;
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", name);
        requestParams.put("country", country);
        requestParams.put("role", UserRoleEnum.ROLE_USER);
        requestParams.put("password", password);

        String uri = baseUrl + "user";
        JsonPath response = RestTests.post(uri, requestParams);
        Long newUserId = Long.valueOf((response).get("id").toString());

        uri += "/" + newUserId;
        String result = RestTests.delete(uri);
        assertEquals(newUserId, Long.valueOf(result));
    }

    @Test(dependsOnMethods = "createIdea", enabled = true)
    public void deleteIdea(){
        Idea idea = new Idea();
        idea.setShortDescription("ShortDescription" + random);
        idea.setStatus("new");
        idea.setUserId(userId.toString());
        Long newIdeaId = ideaRepository.save(idea).getId();

        String uri = baseUrl + "idea/" + newIdeaId;
        String result = RestTests.delete(uri);
        assertEquals(newIdeaId, Long.valueOf(result));
    }

    @Test(dependsOnMethods = "createUser", enabled = true)
	public void getUserByName() throws JSONException {
		User user = userRepository.findOne(userId);
		String userName = user.getName();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", userName);

		String uri = baseUrl + "getUserByName/" + user.getName();
		JsonPath response = RestTests.get(uri);

		List <String> res = response.get("name");
		String newName = res.get(0);

		assertEquals(userName, newName);

	}
}
