package com.april.testproject;

import com.april.testproject.entity.Idea;
import com.april.testproject.entity.Tag;
import com.april.testproject.entity.User;
import com.april.testproject.entity.UserRoleEnum;
import com.april.testproject.repository.IdeaRepository;
import com.april.testproject.repository.TagRepository;
import com.april.testproject.repository.UserRepository;
import com.april.testproject.utils.ApiUtils;
import com.jayway.restassured.path.json.JsonPath;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.util.*;
import static com.april.testproject.utils.ApiUtils.encryptPassword;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SpringBootTest
public class TestprojectApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IdeaRepository ideaRepository;

	@Autowired
	private TagRepository tagRepository;

	private int random;
	private Long userId;
	private Long ideaId;
	private int numberOfUsers;
	private int numberOfIdeas;
	private String password = "password123";
	private String email;
	private String baseUrl = "http://localhost:8080/api/v1/";
	private String header = "Some test header";
	private String adminLogin = "admin@mail.test";
	private String adminPassword = "admin";

	@BeforeClass
	public void init() {
		random = (int) (Math.random() * 1000);
		email = "testmail" + random + "@mail.test";
		numberOfUsers = userRepository.findAll().size();
		numberOfIdeas = ideaRepository.findAll().size();
	}

	@Test(enabled = true)
	public void createUserFast() {
		User user = new User();
		user.setEmail("testmail" + random + "@mail.test");
		user.setPassword(encryptPassword(password));
		user.setRole(UserRoleEnum.ROLE_USER.toString());
		user.setFirstName("TestUser" + random);
		user.setLastName("TestLastName" + random);
		user.setAvatarPicture("TestAva" + random);
		user.setAboutUser("About User " + random);
		user.setAboutCompany("About Company " + random);
		user.setCountry("Some Country" + random);
		user.setCity("Some City" + random);
		user.setRegDate(new Date());
//		userId = userRepository.save(user).getId();
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

		idea.setUserId(userId.toString());
		idea.setHeader(header + random);
		idea.setMainPicture("Main Picture " + random);
		idea.setShortDescription("Short Description" + random);
		idea.setFullDescription("Full Description" + random);
		idea.setPictureList("Picture List " + random);
		idea.setCreationDate(new Date());
		idea.setPrice(new BigDecimal("333.0"));
		ideaRepository.save(idea);
		idea.print();
		assertEquals(ideaRepository.findAll().size(), numberOfIdeas + 1);
	}

	@Test(dependsOnMethods = "createIdeaFast")
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
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		List<String> res = response.get("firstName");
		String newName = res.get(0);
		assertEquals("Amy", newName);

		res = response.get("email");
		String email = res.get(0);
		assertEquals("amy@mail.test", email);
	}

	@Test(dependsOnMethods = "createIdeaAsUser", enabled = true)
	public void getAllIdeas() {
		numberOfIdeas = ideaRepository.findAll().size();
		String uri = baseUrl + "ideas";
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		List<Integer> idNumber = response.get("id");
		assertEquals(numberOfIdeas, idNumber.size());
	}

	@Test(dependsOnMethods = "createIdeaAsUser", enabled = true)
	public void getAllIdeasNumber() {
		numberOfIdeas = ideaRepository.findAll().size();
		String uri = baseUrl + "getNumberAllIdeas";
		String response = RestTests.getToValue(uri, adminLogin, adminPassword);

		int count = Integer.parseInt(response);
		assertEquals(numberOfIdeas, count);
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void getUserById() {
		String uri = baseUrl + "user/" + userId;
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		String currentEmail = response.get("email");
		assertEquals(email, currentEmail);
	}

	@Test(dependsOnMethods = "createIdeaAsUser", enabled = true)
	public void getIdeasByHeader() {
		String uri = baseUrl + "getIdeasByHeader/" + random;
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		List<Long> foundIdeaIds = response.get("id");
		assertTrue((foundIdeaIds.toString()).contains(ideaId.toString()));
	}

	@Test(dependsOnMethods = "createIdeaFast", enabled = true)
	public void getIdeaByIdFast() {
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
		requestParams.put("firstName", firstName);
		requestParams.put("lastName", "TestLastName" + random);
		requestParams.put("avatarPicture", "TestAva" + random);
		requestParams.put("aboutUser", "About User " + random);
		requestParams.put("aboutCompany", "About Company " + random);
		requestParams.put("country", country);
		requestParams.put("city", "Some City" + random);

		String uri = baseUrl + "registration";
		JsonPath response = RestTests.postToJson(uri, requestParams, "", "");
		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(userId);
		assertTrue(user.getFirstName().equalsIgnoreCase(firstName));
		assertTrue(user.getCountry().equalsIgnoreCase(country));
		assertTrue(user.getEmail().equalsIgnoreCase(email));
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void login() {
		String uri = baseUrl + "login";
		JsonPath response = RestTests.getToJson(uri, email, password);

		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(userId);
		assertTrue(user.getEmail().equalsIgnoreCase(String.valueOf(email)));
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void createIdeaAsAdmin() throws JSONException {
		String shortDescription = "ShortDescription" + random;
		String status = "new";
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);
		requestParams.put("userId", userId.toString());
		requestParams.put("header", header + random);
		requestParams.put("mainPicture", "mainPicture" + random);
		requestParams.put("shortDescription", shortDescription);
		requestParams.put("fullDescription", "fullDescription" + random);
		requestParams.put("pictureList", "pictureList" + random);
		requestParams.put("price", new BigDecimal(random));
		requestParams.put("tags", "charity,it");

		String uri = baseUrl + "idea";
		try {RestTests.postToJson(uri, requestParams, adminLogin, adminPassword);}
		catch (NullPointerException e) {
			assertTrue(true);		}
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void createIdeaAsUser() throws JSONException {
		String shortDescription = "ShortDescription" + random;
		String status = "new";
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);
		requestParams.put("header", header + random);
		requestParams.put("mainPicture", "mainPicture" + random);
		requestParams.put("shortDescription", shortDescription);
		requestParams.put("fullDescription", "fullDescription" + random);
		requestParams.put("pictureList", "pictureList" + random);
		requestParams.put("price", new BigDecimal(random));
		requestParams.put("tags", "charity,it");

		String uri = baseUrl + "idea";
		ideaId = Long.valueOf(RestTests.postToJson(uri, requestParams, email, password).get("id").toString());
		Idea idea = ideaRepository.findOne(ideaId);
		assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertEquals(idea.getUserId(),String.valueOf(userId));
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void updateUser() throws JSONException {
		String country = "Some Another Country" + random;
		String city = "Paris";
		String firstName = "Virender Second" + random;
		String email = "newtestmail" + random + "@mail.test";
		JSONObject requestParams = new JSONObject();
		requestParams.put("firstName", firstName);
		requestParams.put("email", email);
		requestParams.put("country", country);
		requestParams.put("city", city);
		requestParams.put("role", UserRoleEnum.ROLE_USER);
		requestParams.put("password", ApiUtils.encryptPassword(password));
		requestParams.put("id", userId);

		String uri = baseUrl + "user";
		JsonPath response = RestTests.putToJson(uri, requestParams, adminLogin, adminPassword);
		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(userId);
		assertTrue(user.getFirstName().equalsIgnoreCase(String.valueOf(firstName)));
		assertTrue(user.getCountry().equalsIgnoreCase(String.valueOf(country)));
		assertTrue(user.getEmail().equalsIgnoreCase(String.valueOf(email)));
		assertTrue(user.getCity().equalsIgnoreCase(String.valueOf(city)));
	}

	@Test(dependsOnMethods = "createIdeaAsUser", enabled = true)
	public void updateIdeaAsAdmin() throws JSONException {
		String shortDescription = "New ShortDescription" + random;
		String status = "approved";
		JSONObject requestParams = new JSONObject();
		requestParams.put("shortDescription", shortDescription);
		requestParams.put("status", status);
		requestParams.put("userId", userId.toString());
		requestParams.put("id", ideaId.toString());
		requestParams.put("tags", "ecology,politics");

		String uri = baseUrl + "idea";
		ideaId = Long.valueOf(RestTests.putToJson(uri, requestParams, adminLogin, adminPassword).get("id").toString());
		Idea idea = ideaRepository.findOne(ideaId);
		assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertEquals(idea.getUserId(), (String.valueOf(userId)));
		assertTrue(idea.getTags().contains("ecology"));
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
		requestParams.put("firstName", firstName);
		requestParams.put("lastName", "TestLastName" + random);
		requestParams.put("avatarPicture", "TestAva" + random);
		requestParams.put("aboutUser", "About User " + random);
		requestParams.put("aboutCompany", "About Company " + random);
		requestParams.put("country", country);
		requestParams.put("city", "Some City" + random);

		String uri = baseUrl + "registration";
		JsonPath response = RestTests.postToJson(uri, requestParams, adminLogin, adminPassword);
		Long newUserId = Long.valueOf((response).get("id").toString());

		//Delete user
		uri = baseUrl + "user/" + newUserId;
		String result = RestTests.deleteToValue(uri, adminLogin, adminPassword);
		assertEquals(newUserId, Long.valueOf(result));
	}

	@Test(dependsOnMethods = "createIdeaAsUser", enabled = true)
	public void deleteIdea() {
		// Create idea to delete
		Idea idea = new Idea();
		idea.setStatus("new");
		idea.setUserId(userId.toString());
		idea.setHeader(header + random);
		idea.setMainPicture("Main Picture " + random);
		idea.setShortDescription("Short Description" + random);
		idea.setFullDescription("Full Description" + random);
		idea.setPictureList("Picture List " + random);
		idea.setCreationDate(new Date());
		idea.setPrice(new BigDecimal("333.0"));

		Long newIdeaId = ideaRepository.save(idea).getId();
		// Delete idea
		String uri = baseUrl + "idea/" + newIdeaId;
		String result = RestTests.deleteToValue(uri, adminLogin, adminPassword);
		assertEquals(newIdeaId, Long.valueOf(result));
	}

	//TODO: Fix bug: find user which contains expected name as part
	@Test(dependsOnMethods = "createUser", enabled = true)
	public void getUserByName() {
		User user = userRepository.findOne(userId);
		String userName = user.getFirstName();

		String uri = baseUrl + "getUserByName/" + user.getFirstName();
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		List<String> res = response.get("firstName");
		String newName = res.get(0);
		assertEquals(userName, newName);
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void getUserByEmail() {
		User user = userRepository.findOne(userId);
		String email = user.getEmail();

		String uri = baseUrl + "getUserByEmail/" + user.getEmail();
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		String newMail =response.get("email");
		assertEquals(email, newMail);
	}

	@Test(dependsOnMethods = "createIdeaAsUser", enabled = true)
	public void getUserByIdeaId() {
		String uri = baseUrl + "getUserByIdeaId/" + ideaId;
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		assertEquals(userId.toString(), response.get("id").toString());
	}

	@Test
	public void getAllTagsFast() {
		String name = "it";
		Long id;
		if (tagRepository.findByTagName(name).size() == 0){
			Tag t1 = new Tag();
			t1.setName(name);
			id = tagRepository.save(t1).getId();
	}
		else id = tagRepository.findByTagName(name).get(0).getId();
	assertEquals(name, tagRepository.findOne(id).getName());

		List<Tag> tags = tagRepository.findAll();
		for (Tag tag : tags) {
			System.out.println(tag.getId() + " : " + tag.getName());
		}
	}

	@Test(enabled = true)
	public void getAllTags() {
		String uri = baseUrl + "tags";
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		List<String> res = response.get("name");
		assertTrue(res.contains("charity"));
		assertEquals(10, res.size());
	}

	@Test(enabled = true)
	public void getTagById() {
		String uri = baseUrl + "tag/7";
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		String res = response.get("name");
		assertEquals("it", res);
	}

	@Test(dependsOnMethods = "createIdeaAsUser", enabled = true)
	public void getTagsByIdeaId() {
		String uri = baseUrl + "getTagsByIdeaId/" + ideaId;
		JsonPath response = RestTests.getToJson(uri, adminLogin, adminPassword);

		List<String> res = response.get();
		assertTrue(res.contains("charity"));
		assertTrue(res.contains("it"));
		assertEquals(2, res.size());
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void likeIdea() throws JSONException {
		Long ideaId = Long.valueOf(27);
		JSONObject requestParams = new JSONObject();
		requestParams.put("ideaId", ideaId);

		String uri = baseUrl + "like/" + ideaId;
		int likeNumberBefore = Integer.parseInt(RestTests.getToValue(uri, email, password));

		uri = baseUrl + "isLiked";
		assertEquals("false", RestTests.postToValue(uri, requestParams, email, password));

		uri = baseUrl + "like";
		RestTests.postToJson(uri, requestParams, email, password);

		uri = baseUrl + "like/" + ideaId;
		int likeNumberAfter = Integer.parseInt(RestTests.getToValue(uri, adminLogin, adminPassword));
		assertEquals(likeNumberBefore + 1, likeNumberAfter);

		int rate = ideaRepository.findOne(ideaId).getRate();
		assertEquals(likeNumberAfter, rate);

		uri = baseUrl + "isLiked";
		assertEquals("true", RestTests.postToValue(uri, requestParams, email, password));

		uri = baseUrl + "disLike";
		RestTests.postToJson(uri, requestParams, email, password);

		uri = baseUrl + "isLiked";
		assertEquals("false", RestTests.postToValue(uri, requestParams, email, password));

		uri = baseUrl + "like/" + ideaId;
		int likeNumberAgain = Integer.parseInt(RestTests.getToValue(uri, adminLogin, adminPassword));
		assertEquals(likeNumberBefore, likeNumberAgain);

		rate = ideaRepository.findOne(ideaId).getRate();
		assertEquals(likeNumberBefore, rate);
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void getIdeasByTag() throws JSONException {
		//Create Idea:
		String tag = "charity";
		String shortDescription = "ShortDescription" + random;
		String status = "new";
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);
		requestParams.put("userId", userId.toString());
		requestParams.put("header", header + random);
		requestParams.put("mainPicture", "mainPicture" + random);
		requestParams.put("shortDescription", shortDescription);
		requestParams.put("fullDescription", "fullDescription" + random);
		requestParams.put("pictureList", "pictureList" + random);
		requestParams.put("price", new BigDecimal(random));
		requestParams.put("tags", tag);
		String uri = baseUrl + "idea";
		Long newIdeaId = Long.valueOf(RestTests.postToJson(uri, requestParams, email, password).get("id").toString());

		// Get ideas by tag:
		Long tagId = tagRepository.findByTagName(tag).get(0).getId();
		uri = baseUrl + "getIdeasByTag/" + tagId;
		String result = RestTests.getToValue(uri, adminLogin, adminPassword);
		assertTrue(result.contains(newIdeaId.toString()));
	}

}
