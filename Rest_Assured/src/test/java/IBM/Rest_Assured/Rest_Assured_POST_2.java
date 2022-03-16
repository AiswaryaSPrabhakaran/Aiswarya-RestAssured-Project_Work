package IBM.Rest_Assured;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import POJO.pojoclass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Rest_Assured_POST_2 {
	
	@Test (enabled = false)
	public void JSONtoStr()
		{
			RestAssured.baseURI= "http://localhost:3000";
			String reqbody = "{\"course\":\"IBMUpSkill\",\"purpose\":\"Training\",\"name\":\"A\",\"email\":\"abc@a.com\",\"duration\":\"1year\",\"mode\":\"online\"}";
			RestAssured
				.given()
					.contentType(ContentType.JSON)
				.body(reqbody)
				.when()
					.post("/demo")
				.then().statusCode(201).log().all();
				}


	@Test(enabled = false)
	public void JSONPayload() throws IOException
	{
		FileInputStream file = new FileInputStream("\\Users\\IBMADMIN\\db.json"); //Source location from where the data is fetched
		RestAssured.baseURI = "http://localhost:3000";
		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(IOUtils.toString(file,"UTF-8"))
			.post("/payload_request") //It will get pasted or posted or created to this location http://localhost:3000/payload_request
		.then()
			.statusCode(201)
			.log()
			.all();	
		
	}
	
	@Test(enabled = false)
	public void jsonobject()
	{
		JSONObject obj = new JSONObject();
		obj.put("username", "Ais");
		obj.put("address", "abc");
		obj.put("city", "Bangalore");
		
	RestAssured.baseURI = "http://localhost:3000";
	RestAssured.given()
		.contentType(ContentType.JSON)
		.body(obj.toJSONString())
	.when()
		.post("/payload_request")
	.then()
		.statusCode(201).log().all();
	}
	
	@Test(enabled = false)
	public void complexpayload()
	{
		JSONObject obj = new JSONObject();
		obj.put("username", "Ais");
		obj.put("address", "abc");
		obj.put("city", "Bangalore");
		
		JSONObject tags = new JSONObject();
		tags.put("email", "abc@a.com");
		tags.put("id", 367);
		tags.put("name","efg");
		
		obj.put("tags", tags);
		
		JSONArray arr = new JSONArray();
		arr.add("abc");
		arr.add("xyz");
		arr.add("cde");
		
		obj.put("codes", arr);
		System.out.println(obj);
		RestAssured.baseURI = "http://localhost:3000";
		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(obj.toJSONString())
		.when()
			.post("/payload_request")
		.then()
			.statusCode(201).log().all();
	
	}
	
	@Test(enabled = true)
	public void pojo() throws JsonProcessingException
	{
		pojoclass pojoobj = new pojoclass();
		//Declaring the values
		pojoobj.setUsername("Aiswarya");
		pojoobj.setDepartment("IT");
		pojoobj.setCity("Bangalore");
		
		//Mapping these values to the request body
		
		ObjectMapper obj = new ObjectMapper();
		String body = obj.writerWithDefaultPrettyPrinter().writeValueAsString(pojoobj);
		System.out.println(body);	
		
		// use in the request body
		RestAssured.baseURI = "http://localhost:3000";
		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(body)
		.when()
			.post("/payload_request")
		.then()
			.statusCode(201).log().all();
		
	}
	
	}