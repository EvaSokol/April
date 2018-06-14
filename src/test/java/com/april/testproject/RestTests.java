package com.april.testproject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Eva Sokolyanskaya on 05/05/2018.
 */
public class RestTests{

    static JsonPath post(String uri, JSONObject jsonObject){
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given();
        request.body(jsonObject.toString());
        request.contentType("application/json");
        request.authentication().basic("admin@mail.test", "admin");
        Response response = request.post();

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());
        JsonPath jsonPathEvaluator = response.jsonPath();
        int id = jsonPathEvaluator.get("id");
        System.out.println(id);
        return jsonPathEvaluator;
    }

	static JsonPath postAsUser(String uri, JSONObject jsonObject, String login, String password){
		RestAssured.baseURI = uri;
		RequestSpecification request = RestAssured.given();
		request.body(jsonObject.toString());
		request.contentType("application/json");
		request.authentication().basic(login, password);
		Response response = request.post();

		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asString());
		JsonPath jsonPathEvaluator = response.jsonPath();
		int id = jsonPathEvaluator.get("id");
		System.out.println(id);
		return jsonPathEvaluator;
	}

    static JsonPath put(String uri, JSONObject jsonObject){
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given();
        request.body(jsonObject.toString());
        request.contentType("application/json");
        request.authentication().basic("admin@mail.test", "admin");
        Response response = request.put();

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());
        JsonPath jsonPathEvaluator = response.jsonPath();
        int id = jsonPathEvaluator.get("id");
        System.out.println(id);
        return jsonPathEvaluator;
    }

    static String delete(String uri) {
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given();
        request.contentType("application/json");
        request.authentication().basic("admin@mail.test", "admin");
        Response response = request.delete();
        return response.asString();
    }

    public static JsonPath get(String uri) {
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given();
        request.contentType("application/json");
        request.authentication().basic("admin@mail.test", "admin");
        Response response = request.get();
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator;
    }

    public static String getFromJson(JsonPath json, String fieldName){
        return json.get(fieldName);
    }
}


