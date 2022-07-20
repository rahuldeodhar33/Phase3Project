package Phase3End;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CollectionPhase3 {
	String BaseURI = "http://54.159.169.211:8088/employees/";
	Response res;

	@Test
	public void Collection() {
		// Get request
		res = getMethodAll();
		Assert.assertEquals(res.getStatusCode(), 200);
		System.out.println("Status code for get all employee is :" + res.getStatusCode());
		// Post Request
		res = postMethod("Sunil", "Joshi", "3000", "sj@gmail.com");
		Assert.assertEquals(res.getStatusCode(), 201);
		JsonPath jpath = res.jsonPath();
		int ID = jpath.get("id");
		System.out.println("Newly added EMP ID is : " + ID);
		//Put Request
		res = putMethod(ID,"Manoj", "Joshi", "3000", "Mj@gmail.com");
		Assert.assertEquals(res.getStatusCode(), 200);
	    jpath = res.jsonPath();
	    Assert.assertEquals(jpath.get("firstName"),"Manoj");
	    System.out.println("New employee added. "+ jpath.get("firstName"));
	    //Delete Employee
	    res = deleteMethod(ID);
	    Assert.assertEquals(res.getStatusCode(), 200);
	    System.out.println("Employee record is deleted. " + res.getStatusCode());
	    //Get the deleted employee
	    res = getDeletedEmp(ID);
	    Assert.assertEquals(res.getStatusCode(), 400);
	    System.out.println("Employee not found " + res.getStatusCode());
	}

	public Response getMethodAll() {

		RestAssured.baseURI = BaseURI;
		// Get URI
		RequestSpecification request = RestAssured.given();
		// Use GET method to send response
		Response res = request.get();
		return res;
	}

	// post request to add new employee
	public Response postMethod(String FName, String LName, String Sal, String email) {
		RestAssured.baseURI = BaseURI;
		Map<String, Object> MapObj = new HashMap<String, Object>();
		MapObj.put("firstName", FName);
		MapObj.put("lastName", LName);
		MapObj.put("salary", Sal);
		MapObj.put("email", email);
		// Get URI
		RequestSpecification request = RestAssured.given();
		Response res = request.contentType(ContentType.JSON)
								.accept(ContentType.JSON)
								.body(MapObj)
								.post("");
		return res;
	}
	//Update newly added employee
	public Response putMethod (int ID, String FName, String LName, String Sal, String email) {
		RestAssured.baseURI=BaseURI;
		System.out.println("Update this employee : " + ID);
		Map<String,Object> MapObj = new HashMap<String,Object>();
		MapObj.put("firstName", FName);
		MapObj.put("lastName", LName);
		MapObj.put("salary", Sal);
		MapObj.put("email", email);
		//Get URI
		RequestSpecification request = RestAssured.given();
		Response res = request.contentType(ContentType.JSON)
								.accept(ContentType.JSON)
								.body(MapObj)
								.put("/" + ID);
		return res;
		
	}
	//Delete Employee
	public Response deleteMethod (int ID) {
		RestAssured.baseURI=BaseURI;
		RequestSpecification request = RestAssured.given();
		Response res = request.delete("/" + ID);
		return res;
	}
	//Get the deleted employee
	public Response getDeletedEmp(int ID) {

		RestAssured.baseURI=BaseURI;
		//Get URI
		RequestSpecification request = RestAssured.given();
		//Use GET method to send response 
		Response res = request.get("/" + ID);
		return res;
	}
}

