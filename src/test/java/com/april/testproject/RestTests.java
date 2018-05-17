package com.april.testproject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONObject;

/**
 * Created by Eva Sokolyanskaya on 05/05/2018.
 */
public class RestTests{

    static JsonPath post(String uri, JSONObject jsonObject){
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given();
        request.body(jsonObject.toString());
        request.contentType("application/json");
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
        Response response = request.put();

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());
        JsonPath jsonPathEvaluator = response.jsonPath();
        int id = jsonPathEvaluator.get("id");
        System.out.println(id);
        return jsonPathEvaluator;
    }

    public static String getFromJson(JsonPath json, String fieldName){
        return json.get(fieldName);
    }
}


