package IBM.Rest_Assured;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Rest_Assured_Demo_1 {

	@Test (enabled = false)
	public void testcase()
	{
		Response res = RestAssured.get("http://localhost:3000/demo");
		String responsebody = res.asString(); // To convert the json to string
		System.out.println(responsebody); 		//To print the response body
		System.out.println(res.getStatusCode()); // To print the status code
		System.out.println(res.getStatusLine()); // To print the entire status line
		System.out.println(res.getHeaders()); 	// To print the headers
		System.out.println(res.jsonPath().getString("course")); // To print the value of address
		
	}
	
	@Test
	public void UserLoginAndLogout() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		RestAssured
			.given()
				.contentType(ContentType.JSON).queryParam("username","AB").queryParam("password","AB123") // For Query Parameter
			.when()
				.get("/user/login").then().statusCode(200).log().all();
		System.out.println("Login is successfull");
		RestAssured
			.given()
				.contentType(ContentType.JSON).given().get("/user/logout").then().statusCode(200).log().all();
		System.out.println("Logout is successfull");
	}
}
