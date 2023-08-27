package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;

import io.restassured.response.Response;

public class getToken {

	@Test(priority = 1)
	public String getTokens(String token1) {
		
    JSONObject request=new JSONObject();
		
		request.put("username", "admin");
		request.put("password","password123");
		
		baseURI="https://restful-booker.herokuapp.com";
     String token=
		given().body(request.toJSONString()).headers("Content-Type","application/json").
		when().post("/auth").
		then().statusCode(200).log().all().extract().body().asString().substring(10, 25);
       token1= "token="+token;
       System.out.println("Header: "+token1);
       
       return token1;
		}
	
	
}
