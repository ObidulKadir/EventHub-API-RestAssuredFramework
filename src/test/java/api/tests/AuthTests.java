package api.tests;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.AuthEndpoints;
import api.payload.AuthPayload;
import io.restassured.response.Response;

public class AuthTests {

	Faker faker;
	AuthPayload authPayload;
	String bearer_token;

	@BeforeClass
	public void setup_data() {

		faker = new Faker();
		authPayload = new AuthPayload();

		authPayload.setEmail(faker.internet().emailAddress());

		String password = faker.name().firstName() + Math.floor(Math.random() * 100);
		authPayload.setPassword(password);

	}

	@Test(priority = 1)
	public void testPostUser() {

		Response response = AuthEndpoints.register_user(authPayload);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
	}

	@Test(priority = 2 , dependsOnMethods = {"testPostUser"})
	public void testPostLoginUser(ITestContext context) {

		Response response = AuthEndpoints.login_user(authPayload);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
		bearer_token = response.jsonPath().getString("token");
		
		context.getSuite().setAttribute("bearer_token", bearer_token);
		
		System.out.println("The bearer token after login : "+bearer_token);
		
	}
	
	@Test(priority = 3, dependsOnMethods = {"testPostLoginUser"})
	public void testAuthenticateUser() {
		
		System.out.println("The bearer token in testAuthenticateUser: "+bearer_token);

		Response response = AuthEndpoints.authenticate_user(bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
		Assert.assertEquals(response.jsonPath().getString("user.email"), authPayload.getEmail());
	}

}
