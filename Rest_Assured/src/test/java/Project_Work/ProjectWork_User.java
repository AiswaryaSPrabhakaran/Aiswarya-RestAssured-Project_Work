package Project_Work;

import java.util.Map;

import org.codehaus.groovy.runtime.callsite.PojoMetaMethodSite.PojoCachedMethodSite;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import POJO.pojoclass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import junit.framework.Assert;


public class ProjectWork_User {
	
	@Test (enabled = true)
	public void datatransfer(ITestContext field) 
	{
		String username = "Aishuwar";
		field.setAttribute("username", username);
	}
	
	
	@Test (dependsOnMethods = {"datatransfer"})
	public void CreateUser(ITestContext field)
	{
		String username = (String) field.getAttribute("username");
		JSONObject obj = new JSONObject();
		obj.put("id", 0);
		obj.put("username", username);
		obj.put("firstName", "Aiswarya");
		obj.put("lastName", "Prabhakaran");
		obj.put("email", "abc@a.com");
		obj.put("password", "password");
		obj.put("phone", "123456789");
		obj.put("userStatus", 0);		
		
		
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		Response respobj = RestAssured.given()
			.contentType(ContentType.JSON)
			.body(obj.toJSONString())
		.when()
			.post("/user")
		.then()
			.statusCode(200)
			.log()
			.all().extract().response();
		
		int code = respobj.jsonPath().getInt("code");
		Assert.assertEquals(username, "Aishuwar");
		Assert.assertEquals(code, 200);
		System.out.println(obj.toJSONString() +"\n");
		System.out.println("Username : " +username + " created successfully " +"\n");
		System.out.println("Status is " +respobj.getStatusLine() +"\n");
		
	}
	
		
	@Test(dependsOnMethods = {"CreateUser"})
	public void ModifyUser(ITestContext	field) throws JsonProcessingException
		{
		String username = (String) field.getAttribute("username");
		ProjectWork_User_Pojo pojoobj = new ProjectWork_User_Pojo();
		pojoobj.setEmail("abcde@at.com");
		pojoobj.setPhone("123456");
		ObjectMapper obj = new ObjectMapper();
		String body = obj.writerWithDefaultPrettyPrinter().writeValueAsString(pojoobj);
		//System.out.println(body);
				
		System.out.println("Username for Modified User is " +username);
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		Response respobj = RestAssured.given()
			.contentType(ContentType.JSON)
			.body(body)
		.when()
			.put("/user/" +username)
		.then()
			.statusCode(200)
			.log()
			.all().extract().response();
		 
		int code = respobj.jsonPath().getInt("code");
		Assert.assertEquals(code, 200);
		
		System.out.println("Following fields are updated for the user : " +username+ "\n" +body +"\n");		
		System.out.println("Status is " +respobj.getStatusLine() +"\n");
		}
	
	@Test(dependsOnMethods = {"ModifyUser"})
	public void UserLogin(ITestContext field) 
	{
		String username = (String) field.getAttribute("username");
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		Response respobj = RestAssured.given()
			.contentType(ContentType.JSON).queryParam("username", username).queryParam("password", "password")
		.when()
			.get("/user/login")
		.then()
			.statusCode(200)
			.log()
			.all().extract().response();
		
		int code = respobj.jsonPath().getInt("code");
		Assert.assertEquals(code, 200);
		System.out.println("User logged in Successfully" +"\n");
	}
	
	@Test(dependsOnMethods = {"UserLogin"})
	public void UserLogout(ITestContext field) 
	{
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		Response respobj = RestAssured.given()
			.contentType(ContentType.JSON)
		.when()
			.get("/user/logout")
		.then()
			.statusCode(200)
			.log()
			.all().extract().response();
		
		int code = respobj.jsonPath().getInt("code");
		String message = respobj.jsonPath().getString("message");
		Assert.assertEquals(code, 200);
		Assert.assertEquals(message, "ok");
		System.out.println("User logged in Successfully" +"\n");
	}
	
	@Test(dependsOnMethods = {"UserLogout"})
	public void DeleteUser(ITestContext field)
	{
		String username = (String) field.getAttribute("username");
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		Response respobj = RestAssured.given()
			.contentType(ContentType.JSON)
		.when()
			.delete("/user/" +username)
		.then()
			.statusCode(200)
			.log()
			.all().extract().response();
		
		int code = respobj.jsonPath().getInt("code");
		String message = respobj.jsonPath().getString("message");
		Assert.assertEquals(code, 200);
		Assert.assertEquals(message, username);	
		System.out.println("User deleted Successfully" +"\n");
	}
	
}
