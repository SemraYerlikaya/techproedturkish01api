package techproedturkish01.techproedturkish01api;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AppTest extends TestBase{
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    @Test
	public void get01() {
		given().
		when().
		   get("https://restful-booker.herokuapp.com/booking").
		then().
		   assertThat().
		   statusCode(203);	
	}
    
    @Test
	public void get02() {
		Response response = given().
		                       accept("application/json").
		                    when().
		                       get("https://restful-booker.herokuapp.com/booking/1001");		
		response.
		then().
		assertThat().
		statusCode(404);
	}
    @Test
	public void get03() {
		Response response = given().
				               accept(ContentType.JSON).
				            when().
				               get("http://dummy.restapiexample.com/api/v1/employees"); 
		response.prettyPrint();	
		response.
		   then().
		   assertThat().
		   statusCode(200).
		   contentType(ContentType.JSON).
		   body("data.id", Matchers.hasSize(24)).
		   body("data.employee_name", Matchers.hasItem("Ashton Cox")).
		   body("data.employee_age",Matchers.hasItems("21", "61", "23"));
	}
    
    @Test
	public void delete01() {
		
		Response responseBeforeDelete = given(). 
				                        when().
				                          get("http://dummy.restapiexample.com/api/v1/employee/2");
		responseBeforeDelete.prettyPrint();
		
		Response responseAfterDelete = given().
				                       when().
				                          delete("http://dummy.restapiexample.com/api/v1/delete/2");
		responseAfterDelete.prettyPrint();
		
		Response getResponseAfterDelete = given().
                                             when().
                                             get("http://dummy.restapiexample.com/api/v1/employee/2");
		getResponseAfterDelete.prettyPrint();
		
		responseAfterDelete.
		              then().
		              assertThat().
		              statusCode(200);

	}
    
    @Test
	public void patch01() {
		
		Response responseBeforePatch = given().
				                         spec(spec03).
				                       when().
				                         get("/200");
		
		responseBeforePatch.prettyPrint();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "Kemal Can");
		
		Response responseAfterPatch = given().
				                         contentType(ContentType.JSON).
				                         spec(spec03).
				                         body(jsonObject.toString()).
				                      when().
				                         patch("/200");
		responseAfterPatch.prettyPrint();
		
		responseAfterPatch.
		              then().
		              assertThat().
		              statusCode(200);
		
		JsonPath json = responseAfterPatch.jsonPath();

		SoftAssert softAssert = new SoftAssert();	
		softAssert.assertEquals(json.getString("title"),jsonObject.get("title"));
		softAssert.assertAll();
		
	}
    
    @Test
	public void post01() {
	
		Response response = createRequestBodyByJsonObjectClass();//JSONObject Class kullandik
		
		response.prettyPrint();

		response.
		     then().
		     assertThat().
		     statusCode(200);

		JsonPath json = response.jsonPath();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(json.getString("booking.firstname"), jsonRequestBody.getString("firstname"));
		softAssert.assertEquals(json.getString("booking.lastname"), jsonRequestBody.getString("lastname"));
		softAssert.assertEquals(json.getInt("booking.totalprice"), jsonRequestBody.getInt("totalprice"));
		softAssert.assertEquals(json.getBoolean("booking.depositpaid"), jsonRequestBody.getBoolean("depositpaid"));
		softAssert.assertEquals(json.getString("booking.bookingdates.checkin"), jsonBookingDatesBody.getString("checkin"));
		softAssert.assertEquals(json.getString("booking.bookingdates.checkout"), jsonBookingDatesBody.getString("checkout"));
		softAssert.assertEquals(json.getString("booking.additionalneeds"), jsonRequestBody.getString("additionalneeds"));
		softAssert.assertAll();
	
	}

}
