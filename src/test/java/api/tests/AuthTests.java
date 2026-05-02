package api.tests;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.AuthEndpoints;
import api.payload.AuthPayload;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class AuthTests {

	Faker faker;
	AuthPayload authPayload;
	String bearer_token;
	public Logger logger;

	@BeforeClass
	public void setup_data() {

		logger = LogManager.getLogger(this.getClass());
		logger.debug("------------Debuging--------------");
		logger.info("===== Setup Data Started =====");

		faker = new Faker();
		authPayload = new AuthPayload();

		authPayload.setEmail(faker.internet().emailAddress());

		String password = faker.name().firstName() + Math.floor(Math.random() * 100);
		authPayload.setPassword(password);

		logger.info("Generated Email: " + authPayload.getEmail());
		logger.info("Generated Password: " + authPayload.getPassword());

		logger.info("===== Setup Data Completed =====");
	}

	@Test(priority = 1)
	public void testPostUser() {

		logger.info("************* Creating User ***************");

		Response response = AuthEndpoints.register_user(authPayload);

		logger.info("Request sent for user registration");
		response.then().log().all();

		logger.info("Validating response for user creation");

		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);

		logger.info("************* User Created Successfully ***************");
		// schema validator

		response.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("auth_register_user_response_schema.json"));

		logger.info("************* Response Json validate successfully ***************");
	}

	@Test(priority = 2, dependsOnMethods = { "testPostUser" })
	public void testPostLoginUser(ITestContext context) {

		logger.info("************* Logging in User ***************");

		Response response = AuthEndpoints.login_user(authPayload);

		logger.info("Login request sent");
		response.then().log().all();

		logger.info("Validating login response");

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);

		bearer_token = response.jsonPath().getString("token");
		context.getSuite().setAttribute("bearer_token", bearer_token);

		logger.info("Bearer Token Generated: " + bearer_token);
		logger.info("************* User Login Successful ***************");
	}

	@Test(priority = 3, dependsOnMethods = { "testPostLoginUser" })
	public void testAuthenticateUser() {

		logger.info("************* Authenticating User ***************");

		logger.info("Using Bearer Token: " + bearer_token);

		Response response = AuthEndpoints.authenticate_user(bearer_token);

		logger.info("Authentication request sent");
		response.then().log().all();

		logger.info("Validating authentication response");

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
		Assert.assertEquals(response.jsonPath().getString("user.email"), authPayload.getEmail());

		logger.info("************* User Authentication Successful ***************");
	}
}