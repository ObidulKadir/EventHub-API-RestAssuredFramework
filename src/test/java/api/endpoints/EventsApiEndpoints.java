package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.AuthPayload;
import api.payload.EventPayload;
import io.restassured.response.Response;

public class EventsApiEndpoints {

	// 1. Create event action method.
	public static Response create_event(EventPayload payload, String bearer_token) {

		Response response = given().headers("Authorization", "Bearer " + bearer_token).contentType("application/json")
				.accept("application/json").body(payload)

				.when().post(Roots.create_event_url);

		return response;
	}

	// 2. Read the single event.
	public static Response read_event(int eventId, String bearer_token) {

		Response response = given().headers("Authorization", "Bearer " + bearer_token).contentType("application/json")
				.accept("application/json")
				.pathParam("eventId", eventId)

				.when().get(Roots.read_event_url);

		return response;
	}

	// Update the event
	public static Response update_event(int eventId, String bearer_token, EventPayload payload) {

		Response response = given().headers("Authorization", "Bearer " + bearer_token).contentType("application/json")
				.accept("application/json")
				.body(payload)
				.pathParam("eventId", eventId)

				.when().put(Roots.update_event_url);

		return response;
	}

	// delete the event
	public static Response delete_event(int eventId, String bearer_token) {

		Response response = given().headers("Authorization", "Bearer " + bearer_token).contentType("application/json")
				.accept("application/json")
				.pathParam("eventId", eventId)

				.when().delete(Roots.delete_event_url);

		return response;
	}

}
