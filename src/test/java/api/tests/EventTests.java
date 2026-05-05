package api.tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.EventsApiEndpoints;
import api.payload.EventPayload;
import api.utilities.TokenManager;
import io.restassured.response.Response;

public class EventTests {

	Faker faker;
	EventPayload eventPayload;
	String bearer_token;
	int event_id;

	@BeforeClass
	public void setup_data() {

		faker = new Faker();
		eventPayload = new EventPayload();
		
//		AuthTests authTests = new AuthTests();
//		bearer_token = authTests.getToken();
		
		bearer_token = TokenManager.getToken(); // calling the tokenmanager to generate the token

		
		System.out.println("Bearer Token : "+bearer_token);

		eventPayload.setTitle(faker.book().title());
		eventPayload.setDescription(faker.lorem().sentence());
		eventPayload.setCategory(faker.commerce().department());
		eventPayload.setVenue(faker.company().name());
		eventPayload.setCity(faker.address().city());

		eventPayload.setEventDate("2026-06-15T09:00:00.000Z");

		// Random price (100 - 1000)
		eventPayload.setPrice(faker.number().numberBetween(100, 1000));

		// Random total seats (50 - 500)
		eventPayload.setTotalSeats(faker.number().numberBetween(50, 500));

		eventPayload.setImageUrl(faker.internet().image());

	}

	@Test(priority = 1)
	public void testPostEvent(ITestContext context) {
//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");

		System.out.println("Test Post Event " + bearer_token);
		Response response = EventsApiEndpoints.create_event(eventPayload, bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
		event_id = response.jsonPath().getInt("data.id");

		context.getSuite().setAttribute("event_id", event_id);
	}

	@Test(priority = 2, dependsOnMethods = { "testPostEvent" })
	public void testGetEvent(ITestContext context) {
//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");

		System.out.println("Test Post Event " + bearer_token);
		Response response = EventsApiEndpoints.read_event(event_id, bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
	}

	@Test(priority = 3, dependsOnMethods = { "testGetEvent" })
	public void testUpdateEvent(ITestContext context) {
//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");

		eventPayload = new EventPayload();

		faker = new Faker();
		eventPayload = new EventPayload();

		eventPayload.setTitle(faker.book().title());
		eventPayload.setDescription(faker.lorem().sentence());
		eventPayload.setCategory(faker.commerce().department());
		eventPayload.setVenue(faker.company().name());
		eventPayload.setCity(faker.address().city());

		eventPayload.setEventDate("2026-06-15T09:00:00.000Z");

		// Random price (100 - 1000)
		eventPayload.setPrice(faker.number().numberBetween(100, 1000));

		// Random total seats (50 - 500)
		eventPayload.setTotalSeats(faker.number().numberBetween(50, 500));

		eventPayload.setImageUrl(faker.internet().image());

		Response response = EventsApiEndpoints.update_event(event_id, bearer_token, eventPayload);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
	}

	@Test(priority = 4, dependsOnMethods = { "testUpdateEvent" })
	public void testDeleteEvent(ITestContext context) {
//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");

		Response response = EventsApiEndpoints.delete_event(event_id, bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
	}

}
