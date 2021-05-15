package apiTest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.automation.BaseTest.BaseTest;
import com.automation.utils.ExtentReport;

import io.restassured.response.Response;
import pojo.pojoClass;

public class testApi extends BaseTest{
	
	
	@Test(description="Validate 200 as status code with wrong base URI")
	public void Validate404ForUsers()
	{
		ExtentReport.extentlog = ExtentReport.extentreport.startTest("Validate404ForUsers",
				"status code should be 404");
////		Response resp = given().
//				when().get("https://reqres.in/api/users/876");
		
		Response resp = given().when().get("https://reqres.in/api/users?page=2");
		int statuscode = resp.getStatusCode();
		assertEquals(statuscode,200);
		System.out.println(" Status code for API is:" + statuscode);
	}

	@Test(groups="RegressionSuite")
	public static void putmethodpojo() {
		ExtentReport.extentlog = ExtentReport.extentreport.startTest("putmethodpojo",
				"Validate the job is sdet or not");
		
		   pojoClass pjc = new pojoClass("tester", "sdet");
		
		   Response resp = given().header("Content-Type","application/json").
		            body(pjc).
		            when().
		            post("https://reqres.in/api/users");
		    System.out.println(resp.getBody().asString());
		    assertEquals(resp.path("job"),"sdet");
		    
	}


}
