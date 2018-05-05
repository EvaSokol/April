package com.april.testproject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONObject;

/**
 * Created by Eva Sokolyanskaya on 05/05/2018.
 */
public class RestTests{

    static void post(String uri, JSONObject jsonObject){
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given();
        request.body(jsonObject.toString());
        Response response = request.post();

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }
}
