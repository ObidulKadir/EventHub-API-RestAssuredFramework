package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.AuthPayload;
import io.restassured.response.Response;

public class EventsApiEndpoints {
	
	// 1. Create event action method.
		public static Response create_event(EventPayload payload,String bearer_token) {
			
			Response response = given()
				.headers("Authorization", "Bearer "+bearer_token)
				.contentType("application/json")
				.accept("application/json")
				.body(payload)
				
			.when()
				.post(Roots.create_event);
			
			return response ;
		}
		
		
		
		// 1. Read the single event.
		public static Response read_event(int id,String bearer_token) {
			
			Response response = given()
				.headers("Authorization", "Bearer "+bearer_token)
				.contentType("application/json")
				.accept("application/json")
				.
				
			.when()
				.get(Roots.read_event);
			
			return response ;
		}

}
