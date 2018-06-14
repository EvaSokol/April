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
import java.util.Date;
import java.util.List;
import static com.april.testproject.utils.ApiUtils.encryptPassword;
import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

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
	String email = "testmail" + random + "@mail.test";
	private String baseUrl = "http://localhost:8080/api/v1/";

	@BeforeClass
	public void init() {
		random = (int) (Math.random() * 1000);
		numberOfUsers = userRepository.findAll().size();
//		numberOfIdeas = ideaRepository.findAll().size();
	}

	@Test(enabled = true)
	public void createUserFast() {
		User user = new User();
		user.setEmail("testmail" + random + "@mail.test");
		user.setPassword(encryptPassword(password));
		user.setRole(UserRoleEnum.ROLE_USER.toString());
		user.setTags("tags" + random);
		user.setFirstName("TestUser" + random);
		user.setLastName("TestLastName" + random);
		user.setAvatarPicture("TestAva" + random);
		user.setAboutUser("About User " + random);
		user.setAboutCompany("About Company " + random);
		user.setCountry("Some Country" + random);
		user.setCity("Some City" + random);
		userId = userRepository.save(user).getId();
		user.print();
		assertEquals("Amy", userRepository.findAll().get(0).getFirstName());
	}

	@Test(dependsOnMethods = "createUserFast")
	public void getAllUsersFast() {
		List<User> users = userRepository.findAll();
		for (User us : users) {
			us.print();
		}
		assertTrue(users.size() > 0);
	}

	@Test(dependsOnMethods = "createUserFast")
	public void getUserByIdFast() {
		User user = userRepository.findOne(userId);
		user.print();
		assertTrue(user.getCountry().contains(String.valueOf(random)));
		assertTrue(user.getEmail().contains(String.valueOf("testmail" + random + "@mail.test")));
	}

	@Test(dependsOnMethods = "createUserFast", enabled = true)
	public void createIdeaFast() {
		numberOfIdeas = ideaRepository.findAll().size();
		Idea idea = new Idea();
		idea.setStatus("new");
		idea.setTags("tags" + random);
		idea.setUserId(userId.toString());
		idea.setHeader("Header" + random);
		idea.setMainPicture("Main Picture " + random);
		idea.setShortDescription("Short Description" + random);
		idea.setFullDescription("Full Description" + random);
		idea.setPictureList("Picture List " + random);
		idea.setRate(random);
		idea.setCreationDate(new Date());

		ideaId = ideaRepository.save(idea).getId();
		idea.print();
		assertEquals(ideaRepository.findAll().size(), numberOfIdeas + 1);
	}

	@Test
	public void getAllIdeasFast() {
		List<Idea> ideas = ideaRepository.findAll();
		for (Idea idea : ideas) {
			idea.print();
		}
		assertTrue(ideas.size() > 0);
	}

	@Test(dependsOnMethods = "createIdeaFast")
	public void getIdeasByUserIdFast() {
		List<Idea> ideas = ideaRepository.findByUserId(userId.toString());
		for (Idea idea : ideas) {
			idea.print();
		}
	}

	@Test(enabled = true)
	public void getAllUsers() {
		String uri = baseUrl + "users";
		JsonPath response = RestTests.get(uri);

		List<String> res = response.get("firstName");
		String newName = res.get(0);
		assertEquals("Amy", newName);

		res = response.get("email");
		String email = res.get(0);
		assertEquals("amy@mail.test", email);
	}

	@Test(enabled = true)
	public void getAllIdeas() {
		String uri = baseUrl + "ideas";
		JsonPath response = RestTests.get(uri);

		List<Integer> rates = response.get("rate");
		for (Integer rate : rates) {
			System.out.println(rate);
		}
	}

	@Test(dependsOnMethods = "createIdea", enabled = true)
	public void getIdeaById() {
		Idea idea = ideaRepository.findOne(ideaId);
		idea.print();
		assertTrue(idea.getShortDescription().contains(String.valueOf(random)));
	}

	//TODO: make autostart application for test
	@Test(enabled = true)
	public void createUser() throws Exception {
		String country = "Some Country" + random;
		String firstName = "Virender" + random;
		JSONObject requestParams = new JSONObject();
		requestParams.put("email", email);
		requestParams.put("password", password);
		requestParams.put("role", UserRoleEnum.ROLE_USER);
		requestParams.put("tags", random);
		requestParams.put("firstName", firstName);
		requestParams.put("lastName", "TestLastName" + random);
		requestParams.put("avatarPicture","TestAva" + random);
		requestParams.put("aboutUser", "About User " + random);
		requestParams.put("aboutCompany", "About Company " + random);
		requestParams.put("country", country);
		requestParams.put("city", "Some City" + random);


		String uri = baseUrl + "user";
		JsonPath response = RestTests.post(uri, requestParams);
		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(Long.valueOf(userId));
		assertTrue(user.getFirstName().equalsIgnoreCase(String.valueOf(firstName)));
		assertTrue(user.getCountry().equalsIgnoreCase(String.valueOf(country)));
		assertTrue(user.getEmail().equalsIgnoreCase(String.valueOf(email)));
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void createIdea() throws JSONException {
		String shortDescription = "ShortDescription" + random;
		String status = "new";
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);
		requestParams.put("tags", "tag" + random);
		requestParams.put("userId", userId.toString());
		requestParams.put("header", "header" + random);
		requestParams.put("mainPicture", "mainPicture" + random);
		requestParams.put("shortDescription", shortDescription);
		requestParams.put("fullDescription", "fullDescription" + random);
		requestParams.put("pictureList", "pictureList" + random);
		requestParams.put("rate", random);

		String uri = baseUrl + "idea";
		ideaId = Long.valueOf(RestTests.post(uri, requestParams).get("id").toString());
		Idea idea = ideaRepository.findOne(Long.valueOf(ideaId));
		assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertTrue(idea.getUserId().equals(String.valueOf(userId)));
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void createIdeaAsUser() throws JSONException {
		String shortDescription = "ShortDescription" + random;
		String status = "new";
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);
		requestParams.put("tags", "tag" + random);
		requestParams.put("userId", userId.toString());
		requestParams.put("header", "header" + random);
		requestParams.put("mainPicture", "mainPicture" + random);
		requestParams.put("shortDescription", shortDescription);
		requestParams.put("fullDescription", "fullDescription" + random);
		requestParams.put("pictureList", "pictureList" + random);
		requestParams.put("rate", random);

		String uri = baseUrl + "idea";
		ideaId = Long.valueOf(RestTests.postAsUser(uri, requestParams, email, password).get("id").toString());
		Idea idea = ideaRepository.findOne(Long.valueOf(ideaId));
		assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertTrue(idea.getUserId().equals(String.valueOf(userId)));
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void updateUser() throws JSONException {
		String country = "Some Another Country" + random;
		String firstName = "Virender Second" + random;
		String email = "newtestmail" + random + "@mail.test";
		JSONObject requestParams = new JSONObject();
		requestParams.put("firstName", firstName);
		requestParams.put("email", email);
		requestParams.put("country", country);
		requestParams.put("role", UserRoleEnum.ROLE_USER);
		requestParams.put("password", password);
		requestParams.put("id", userId);

		String uri = baseUrl + "user";
		JsonPath response = RestTests.put(uri, requestParams);
		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(Long.valueOf(userId));
		assertTrue(user.getFirstName().equalsIgnoreCase(String.valueOf(firstName)));
		assertTrue(user.getCountry().equalsIgnoreCase(String.valueOf(country)));
		assertTrue(user.getEmail().equalsIgnoreCase(String.valueOf(email)));
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
		//Create user to delete
		String country = "Some Country" + random;
		String firstName = "Virender" + random;
		String email = "anothertestmail" + random + "@mail.test";
		JSONObject requestParams = new JSONObject();
		requestParams.put("email", email);
		requestParams.put("password", password);
		requestParams.put("role", UserRoleEnum.ROLE_USER);
		requestParams.put("tags", random);
		requestParams.put("firstName", firstName);
		requestParams.put("lastName", "TestLastName" + random);
		requestParams.put("avatarPicture","TestAva" + random);
		requestParams.put("aboutUser", "About User " + random);
		requestParams.put("aboutCompany", "About Company " + random);
		requestParams.put("country", country);
		requestParams.put("city", "Some City" + random);

		String uri = baseUrl + "user";
		JsonPath response = RestTests.post(uri, requestParams);
		Long newUserId = Long.valueOf((response).get("id").toString());

		//Delete user
		uri += "/" + newUserId;
		String result = RestTests.delete(uri);
		assertEquals(newUserId, Long.valueOf(result));
	}

	@Test(dependsOnMethods = "createIdea", enabled = true)
	public void deleteIdea() {
		// Create idea to delete
		Idea idea = new Idea();
		idea.setStatus("new");
		idea.setTags("tags" + random);
		idea.setUserId(userId.toString());
		idea.setHeader("Header" + random);
		idea.setMainPicture("Main Picture " + random);
		idea.setShortDescription("Short Description" + random);
		idea.setFullDescription("Full Description" + random);
		idea.setPictureList("Picture List " + random);

		Long newIdeaId = ideaRepository.save(idea).getId();
		// Delete idea
		String uri = baseUrl + "idea/" + newIdeaId;
		String result = RestTests.delete(uri);
		assertEquals(newIdeaId, Long.valueOf(result));
	}

	//TODO: Fix bug: find user which contains expected name as part
	@Test(dependsOnMethods = "createUser", enabled = true)
	public void getUserByName(){
		User user = userRepository.findOne(userId);
		String userName = user.getFirstName();

		String uri = baseUrl + "getUserByName/" + user.getFirstName();
		JsonPath response = RestTests.get(uri);

		List<String> res = response.get("firstName");
		String newName = res.get(0);

		assertEquals(userName, newName);

	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void getUserByEmail(){
		User user = userRepository.findOne(userId);
		String email = user.getEmail();

		String uri = baseUrl + "getUserByEmail/" + user.getEmail();
		JsonPath response = RestTests.get(uri);

		String res = response.get("email");
		String newMail = res;

		assertEquals(email, newMail);

	}
}
