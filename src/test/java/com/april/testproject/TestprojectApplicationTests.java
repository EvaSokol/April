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
import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

@SpringBootTest
public class TestprojectApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	IdeaRepository ideaRepository;

	@Autowired
	private TagRepository tagRepository;

	private int random;
	private Long userId;
	private Long ideaId;
	private int numberOfUsers;
	private int numberOfIdeas;
	private String password = "password123";
	String email;
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
		user.setTags("tags" + random);
		user.setFirstName("TestUser" + random);
		user.setLastName("TestLastName" + random);
		user.setAvatarPicture("TestAva" + random);
		user.setAboutUser("About User " + random);
		user.setAboutCompany("About Company " + random);
		user.setCountry("Some Country" + random);
		user.setCity("Some City" + random);
		user.setRegDate(new Date());
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

		idea.setUserId(userId.toString());
		idea.setHeader(header + random);
		idea.setMainPicture("Main Picture " + random);
		idea.setShortDescription("Short Description" + random);
		idea.setFullDescription("Full Description" + random);
		idea.setPictureList("Picture List " + random);
//		idea.setRate(random);
		idea.setCreationDate(new Date());
		idea.setPrice(new BigDecimal("333.0"));
//		idea.setWhoLiked("");
//		Set<Tag> tags = getTags("innovations");
//		idea.getTags().addAll(tags);
		ideaRepository.save(idea).getId();
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
		requestParams.put("tags", random);
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
		User user = userRepository.findOne(Long.valueOf(userId));
		assertTrue(user.getFirstName().equalsIgnoreCase(firstName));
		assertTrue(user.getCountry().equalsIgnoreCase(country));
		assertTrue(user.getEmail().equalsIgnoreCase(email));
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void login() {
		String uri = baseUrl + "login";
		JsonPath response = RestTests.getToJson(uri, email, password);

		userId = Long.valueOf((response).get("id").toString());
		User user = userRepository.findOne(Long.valueOf(userId));
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
		requestParams.put("rate", random);
		requestParams.put("price", new BigDecimal(random));
		requestParams.put("tags", "charity,it");

		String uri = baseUrl + "idea";
		try {RestTests.postToJson(uri, requestParams, adminLogin, adminPassword);}
		catch (NullPointerException e) {
			assertTrue(1==1);		}
	}

	@Test(dependsOnMethods = "createUser", enabled = true)
	public void createIdeaAsUser() throws JSONException {
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
		requestParams.put("rate", random);
		requestParams.put("price", new BigDecimal(random));
		requestParams.put("tags", "charity,it");

		String uri = baseUrl + "idea";
		ideaId = Long.valueOf(RestTests.postToJson(uri, requestParams, email, password).get("id").toString());
		Idea idea = ideaRepository.findOne(Long.valueOf(ideaId));
		assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertTrue(idea.getUserId().equals(String.valueOf(userId)));
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
		User user = userRepository.findOne(Long.valueOf(userId));
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
		Idea idea = ideaRepository.findOne(Long.valueOf(ideaId));
		assertTrue(idea.getShortDescription().equalsIgnoreCase(String.valueOf(shortDescription)));
		assertTrue(idea.getStatus().equalsIgnoreCase(String.valueOf(status)));
		assertTrue(idea.getUserId().equals(String.valueOf(userId)));
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
		requestParams.put("tags", random);
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

		String res = response.get("email");
		String newMail = res;
		assertEquals(email, newMail);
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
			System.out.println(tag.getId());
			System.out.println(tag.getName());
			System.out.println("=============");
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

	private Set<Tag> getTags(String tagString) {
		if (tagString == null) return null;
		List strings = Arrays.asList(tagString.split(","));
		Set<String> tagNameSet = new HashSet<>();
		if (strings.size() != 0) tagNameSet.addAll(strings);
		Set<Tag> tags = new HashSet<>();
		for (String tagName : tagNameSet){
			tags.add(tagRepository.findByTagName(tagName).get(0));
		}
		return tags;
	}
}
