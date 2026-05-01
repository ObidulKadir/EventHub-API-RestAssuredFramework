package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.AuthPayload;
import io.restassured.response.Response;

public class AuthEndpoints {
	// action perform method integrate this class.
	
	// 1. Create user action method.
	public static Response register_user(AuthPayload payload) {
		
		Response response = given()
			.contentType("application/json")
			.accept("application/json")
			.body(payload)
			
		.when()
			.post(Roots.register_user);
		
		return response ;
	}
	
	// 2. Login user
	public static Response login_user(AuthPayload payload) {
		
		Response response = given()
			.contentType("application/json")
			.accept("application/json")
			.body(payload)
			
		.when()
			.post(Roots.login_user);
		
		return response ;
	}
	
	// 3. Authenticate the user
		public static Response authenticate_user(String bearer_token) {
			
			Response response = given()
				.headers("Authorization", "Bearer " + bearer_token)
				.accept("application/json")
				
			.when()
				.get(Roots.getAuthenticate_user);
			
			return response ;
		}

}
