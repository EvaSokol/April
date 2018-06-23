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


	static JsonPath postToJson(String uri, JSONObject jsonObject, String login, String password) {
		return methodToJson("post", uri,jsonObject,login, password);
	}

	static String postToValue(String uri, JSONObject jsonObject, String login, String password) {
		return methodToValue("post", uri, jsonObject, login, password);
	}

	static JsonPath putToJson(String uri, JSONObject jsonObject, String login, String password) {
		return methodToJson("put", uri,jsonObject, login, password);
	}

	static String putToValue(String uri, JSONObject jsonObject, String login, String password) {
		return methodToValue("put", uri, jsonObject, login, password);
	}

	static JsonPath getToJson(String uri, String login, String password) {
		return methodToJson("get", uri, null, login, password);
	}

	static String getToValue(String uri, String login, String password) {
		return methodToValue("get", uri, null, login, password);
	}

	static JsonPath deleteToJson(String uri, String login, String password) {
		return methodToJson("delete", uri, null, login, password);
	}

	static String deleteToValue(String uri, String login, String password) {
		return methodToValue("delete", uri, null, login, password);
	}

	static String methodToValue(String method, String uri, JSONObject jsonObject, String login, String password) {
		RestAssured.baseURI = uri;
		RequestSpecification request = RestAssured.given();
		request.contentType("application/json");
		request.authentication().basic(login, password);
		Response response;
		int id = 0;
		switch (method) {
			case "post":
				request.body(jsonObject.toString());
				response = request.post();
				break;
			case "delete":
				response = request.delete();
				break;
			case "put":
				request.body(jsonObject.toString());
				response = request.put();
				break;
			case "get":
				response = request.get();
				break;
			default:
				response = request.get();
				break;
		}
		System.out.println("uri: " + uri);
		if (jsonObject != null) System.out.println("body: " + jsonObject.toString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asString());
		if (id != 0) System.out.println(id);
		return response.asString();
	}

	static JsonPath methodToJson(String method, String uri, JSONObject jsonObject, String login, String password) {
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
				break;
			case "delete":
				response = request.delete();
				break;
			case "put":
				request.body(jsonObject.toString());
				response = request.put();
				break;
			case "get":
				response = request.get();
				break;
			default:
				response = request.get();
				break;
		}
		jsonPathEvaluator = response.jsonPath();
		try {
			id = jsonPathEvaluator.get("id");
			if (id != 0) System.out.println(id);
		}
		catch (ClassCastException e1){}
		catch(IllegalArgumentException e2){
		}
		System.out.println("uri: " + uri);
		if (jsonObject != null) System.out.println("body: " + jsonObject.toString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asString());

		return jsonPathEvaluator;
	}

//	public static String getFromJson(JsonPath json, String fieldName) {
//		return json.get(fieldName);
//	}
}


