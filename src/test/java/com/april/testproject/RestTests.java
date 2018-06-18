package com.april.testproject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONObject;

/**
 * Created by Eva Sokolyanskaya on 05/05/2018.
 */
public class RestTests {

	static JsonPath post(String uri, JSONObject jsonObject) {
		return method("post", uri,jsonObject,"admin@mail.test", "admin");
	}

	static JsonPath postAsUser(String uri, JSONObject jsonObject, String login, String password) {
		return method("post", uri,jsonObject,login, password);
	}

	static JsonPath put(String uri, JSONObject jsonObject) {
		return method("put", uri,jsonObject,"admin@mail.test", "admin");
	}

	static JsonPath putAsUser(String uri, JSONObject jsonObject, String login, String password) {
		return method("put", uri,jsonObject, login, password);
	}

	static String delete(String uri) {
		RestAssured.baseURI = uri;
		RequestSpecification request = RestAssured.given();
		request.contentType("application/json");
		request.authentication().basic("admin@mail.test", "admin");
		Response response = request.delete();
		return response.asString();
	}

//	static JsonPath delete(String uri) {
//		return method("delete", uri, null,"admin@mail.test", "admin");
//	}
//
//	static JsonPath deleteAsUser(String uri, String login, String password) {
//		return method("delete", uri, null, login, password);
//	}

	static JsonPath get(String uri) {
		return method("get", uri, null,"admin@mail.test", "admin");
	}

	static JsonPath getAsUser(String uri, String login, String password) {
		return method("get", uri, null, login, password);
	}

	static JsonPath method(String method, String uri, JSONObject jsonObject, String login, String password) {
		RestAssured.baseURI = uri;
		RequestSpecification request = RestAssured.given();
		request.contentType("application/json");
		request.authentication().basic(login, password);
		Response response;
		JsonPath jsonPathEvaluator;
		int id = 0;
		switch (method) {
			case "post":
				request.body(jsonObject.toString());
				response = request.post();
				jsonPathEvaluator = response.jsonPath();
				id = jsonPathEvaluator.get("id");
				break;
			case "delete":
				response = request.delete();
				jsonPathEvaluator = new JsonPath(response.asString());
				break;
			case "put":
				request.body(jsonObject.toString());
				response = request.put();
				jsonPathEvaluator = response.jsonPath();
				id = jsonPathEvaluator.get("id");
				break;
			case "get":
				response = request.get();
				jsonPathEvaluator = response.jsonPath();
				break;
			default:
				response = request.get();
				jsonPathEvaluator = response.jsonPath();
				break;
		}
		System.out.println("uri: " + uri);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asString());
		if (id != 0) System.out.println(id);
		return jsonPathEvaluator;
	}

	public static String getFromJson(JsonPath json, String fieldName) {
		return json.get(fieldName);
	}
}


