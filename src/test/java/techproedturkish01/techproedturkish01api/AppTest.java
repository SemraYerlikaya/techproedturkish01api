package techproedturkish01.techproedturkish01api;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AppTest{
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
		   statusCode(200);		
	}
    
    @Test
	public void get02() {
		Response response = given().
		                       accept("application/json").
		                    when().
		                       get("https://restful-booker.herokuapp.com/booking/1001");	
		response.prettyPrint();		
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

}
