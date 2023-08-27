package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;

import io.restassured.response.Response;


public class bookingid {
	
	String token1=null;
	
	public String bookingid;
	@Test(priority = 1)
	public void getbookingID() {
		
		baseURI="https://restful-booker.herokuapp.com";
		given().
		    get("/booking").
		then().
		    statusCode(200).
		    contentType("application/json; charset=utf-8");
		                       }
	
	@Test(priority = 2)
	public void postcreatebook() {
		
		JSONObject request=new JSONObject();
		
		request.put("firstname", "Alexandra");
		request.put("lastname","Rogerian");
		request.put("totalprice", "1255");
		request.put("depositpaid", "true");
		
		JSONObject nestedrequest1= new JSONObject();
		nestedrequest1.put("checkin", "2023-10-11");
		nestedrequest1.put("checkout", "2023-11-11");
		
		request.put("bookingdates",nestedrequest1);
		request.put("additionalneeds", "super bowls");
		
		baseURI="https://restful-booker.herokuapp.com";

	bookingid=	given().
		   headers("Content-Type","application/json").
		   body(request.toJSONString()).
		   
		when().
		   post("/booking/").
		   
		then().
		   statusCode(200).header("Content-Type", "application/json; charset=utf-8").log().all().
		   body("booking.firstname", equalTo("Alexandra")).
		   body("booking.lastname", equalTo("Rogerian")).
	 	   body("booking.totalprice", equalTo(1255)).extract().body().asString().substring(13, 17);
	
	System.out.println("Booking ID: "+bookingid);
	
	}

	
	@Test(priority = 3)
	public void getBookingname() {
		
    	baseURI="https://restful-booker.herokuapp.com";
		given().
		    get("/booking/"+bookingid).
		then().
		    statusCode(200).
		    contentType("application/json; charset=utf-8").
			body("firstname", equalTo("Alexandra")).
			body("lastname", equalTo("Rogerian")).
		 	body("totalprice", equalTo(1255));
		}   
	
	@Test(priority = 4)
	public void putBooking() {
		
		getToken gt=new getToken();
		String tok=gt.getTokens(token1);
        System.out.println("Token added in PUT: "+tok+" Booking id:"+bookingid);

      JSONObject request=new JSONObject();
		request.put("firstname", "Ostrichiam");
		request.put("lastname","Roger");
		request.put("totalprice", "12475");
		request.put("depositpaid", "true");
		
		JSONObject nestedrequest1= new JSONObject();
		nestedrequest1.put("checkin", "2024-10-11");
		nestedrequest1.put("checkout", "2024-11-11");
		
		request.put("bookingdates",nestedrequest1);
		request.put("additionalneeds", "super bowling");
		
		baseURI="https://restful-booker.herokuapp.com";
        
		given().
		   headers("Content-Type","application/json").headers("Cookie", tok).
		   body(request.toJSONString()).
		when().
		   put("/booking/"+bookingid).
		then().
		   statusCode(200).header("Content-Type", "application/json; charset=utf-8").
		   body("firstname", equalTo("Ostrichiam")).
		   body("lastname", equalTo("Roger")).
	 	   body("totalprice", equalTo(12475)).log().all();	
		
		System.out.println("Booking ID updated passed:"+tok);
		
	}
	
	@Test(priority = 5)
	public void getBookingname1() {
		
    	baseURI="https://restful-booker.herokuapp.com";
		given().
		    get("/booking/"+bookingid).
		then().
		    statusCode(200).
		    contentType("application/json; charset=utf-8").
		    body("firstname", equalTo("Ostrichiam")).
			body("lastname", equalTo("Roger")).
		 	body("totalprice", equalTo(12475));
		 }   
	

	@Test(priority = 6)
	public void delBooking() {
		getToken gt=new getToken();
		String tok=gt.getTokens(token1);
        System.out.println("Token added in DELETE: "+tok+" Booking id:"+bookingid);
		
		baseURI="https://restful-booker.herokuapp.com";
		given().
		   headers("Content-Type","application/json").
		   headers("Cookie", tok).
		when().
		   delete("/booking/"+bookingid).
		then().
		   statusCode(201).log().all();
		}  
	
	}
