package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static  io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class schemavalidator {
String token1=null;
	
	public String bookingid;
	
	@Test(priority = 1)
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
		   
		then().assertThat().body(matchesJsonSchemaInClasspath("createbookschema.json")).
		   statusCode(200).header("Content-Type", "application/json; charset=utf-8").log().all().
		   body("booking.firstname", equalTo("Alexandra")).extract().body().asString().substring(13, 17);
	
	System.out.println("Booking ID: "+bookingid);
	
	}

	
	@Test(priority = 2)
	public void getBookingname() {
		
    	baseURI="https://restful-booker.herokuapp.com";
		given().
		    get("/booking/"+bookingid).
		then().
		    statusCode(200).assertThat().body(matchesJsonSchemaInClasspath("getbookschema.json"));
		}   
	
	@Test(priority = 3)
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
		   statusCode(200).assertThat().body(matchesJsonSchemaInClasspath("updateschema.json"));	
		
		System.out.println("Validated Schema for PUT:"+tok);
		
	}
	

}
