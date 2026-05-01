package api.tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.BookingEndPoints;
import api.payload.BookingPayload;
import io.restassured.response.Response;

import com.github.javafaker.Faker;

public class BookingTests {

	Faker faker;
	BookingPayload bookingPayload;

	static int bookingId;
	static String bookingRef;
	static String bearer_token;

	// Payload setup here
	@BeforeClass
	public void setup_data() {

		faker = new Faker();
		bookingPayload = new BookingPayload();

		bookingPayload.setEventId(faker.number().numberBetween(1, 10));
		bookingPayload.setCustomerName(faker.name().fullName());
		bookingPayload.setCustomerEmail(faker.internet().emailAddress());
		bookingPayload.setCustomerPhone("+8801" + faker.number().digits(8));
		bookingPayload.setQuantity(faker.number().numberBetween(1, 5));
	}

	// 1. Create Booking
	@Test(priority = 1)
	public void test_create_booking(ITestContext context) {
		bearer_token = (String) context.getSuite().getAttribute("bearer_token");
		
		System.out.println("TOKEN: " + bearer_token);

		Response response = BookingEndPoints.create_booking(bookingPayload, bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);

		bookingId = response.jsonPath().getInt("data.id");
		bookingRef = response.jsonPath().getString("data.bookingRef");
	}

	// 2. Read by ID
	@Test(priority = 2, dependsOnMethods = "test_create_booking")
	public void test_read_booking_by_id(ITestContext context) {
		bearer_token = (String) context.getSuite().getAttribute("bearer_token");

		Response response = BookingEndPoints.read_booking(bookingId, bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
	}

	// 3. Read by Reference
	@Test(priority = 3, dependsOnMethods = "test_create_booking")
	public void test_read_booking_by_ref(ITestContext context) {
		bearer_token = (String) context.getSuite().getAttribute("bearer_token");

		Response response = BookingEndPoints.read_booking_by_ref(bookingRef, bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
	}

	// 4. Delete Booking
	@Test(priority = 4, dependsOnMethods = "test_create_booking")
	public void test_delete_booking(ITestContext context) {
		bearer_token = (String) context.getSuite().getAttribute("bearer_token");

		Response response = BookingEndPoints.delete_booking(bookingId, bearer_token);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
		Assert.assertEquals(response.jsonPath().getString("message"), "Booking cancelled");
	}
}