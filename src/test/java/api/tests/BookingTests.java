package api.tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.BookingEndPoints;
import api.payload.BookingPayload;
import io.restassured.response.Response;

import com.github.javafaker.Faker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingTests {

	Faker faker;
	BookingPayload bookingPayload;

	static int bookingId;
	static String bookingRef;
	static String bearer_token;

	public Logger logger;

	// Payload setup here
	@BeforeClass
	public void setup_data() {

		logger = LogManager.getLogger(this.getClass());
		logger.info("===== Booking Test Setup Started =====");

		faker = new Faker();
		bookingPayload = new BookingPayload();
		
		AuthTests authTest = new AuthTests();
		bearer_token = authTest.getToken();
		
		System.out.println("The bearer token in booking test class "+bearer_token);

		bookingPayload.setEventId(1);
		bookingPayload.setCustomerName(faker.name().fullName());
		bookingPayload.setCustomerEmail(faker.internet().emailAddress());
		bookingPayload.setCustomerPhone("+8801" + faker.number().digits(8));
		bookingPayload.setQuantity(faker.number().numberBetween(1, 5));

		logger.info("Payload Prepared:");
		logger.info("Name: " + bookingPayload.getCustomerName());
		logger.info("Email: " + bookingPayload.getCustomerEmail());
		logger.info("Phone: " + bookingPayload.getCustomerPhone());
		logger.info("Quantity: " + bookingPayload.getQuantity());

		logger.info("===== Booking Test Setup Completed =====");
	}

	// 1. Create Booking
	@Test(priority = 1)
	public void test_create_booking(ITestContext context) {

		logger.info("************* Create Booking Test Started *************");

//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");
		logger.info("Using Bearer Token: " + bearer_token);

		Response response = BookingEndPoints.create_booking(bookingPayload, bearer_token);

		logger.info("Create booking request sent");
		response.then().log().all();

		logger.info("Validating create booking response");

		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);

		bookingId = response.jsonPath().getInt("data.id");
		bookingRef = response.jsonPath().getString("data.bookingRef");

		logger.info("Booking Created Successfully");
		logger.info("Booking ID: " + bookingId);
		logger.info("Booking Ref: " + bookingRef);

		logger.info("************* Create Booking Test Completed *************");
	}

	// 2. Read by ID
	@Test(priority = 2, dependsOnMethods = "test_create_booking")
	public void test_read_booking_by_id(ITestContext context) {

		logger.info("************* Read Booking By ID Test Started *************");

//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");
		logger.info("Using Bearer Token: " + bearer_token);
		logger.info("Booking ID: " + bookingId);

		Response response = BookingEndPoints.read_booking(bookingId, bearer_token);

		logger.info("Read booking by ID request sent");
		response.then().log().all();

		logger.info("Validating response");

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("************* Read Booking By ID Test Completed *************");
	}

	// 3. Read by Reference
	@Test(priority = 3, dependsOnMethods = "test_create_booking")
	public void test_read_booking_by_ref(ITestContext context) {

		logger.info("************* Read Booking By Ref Test Started *************");

//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");
		logger.info("Using Bearer Token: " + bearer_token);
		logger.info("Booking Ref: " + bookingRef);

		Response response = BookingEndPoints.read_booking_by_ref(bookingRef, bearer_token);

		logger.info("Read booking by reference request sent");
		response.then().log().all();

		logger.info("Validating response");

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("************* Read Booking By Ref Test Completed *************");
	}

	// 4. Delete Booking
	@Test(priority = 4, dependsOnMethods = "test_create_booking")
	public void test_delete_booking(ITestContext context) {

		logger.info("************* Delete Booking Test Started *************");

//		bearer_token = (String) context.getSuite().getAttribute("bearer_token");
		logger.info("Using Bearer Token: " + bearer_token);
		logger.info("Booking ID to delete: " + bookingId);

		Response response = BookingEndPoints.delete_booking(bookingId, bearer_token);

		logger.info("Delete booking request sent");
		response.then().log().all();

		logger.info("Validating delete response");

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);
		Assert.assertEquals(response.jsonPath().getString("message"), "Booking cancelled");

		logger.info("Booking Deleted Successfully");

		logger.info("************* Delete Booking Test Completed *************");
	}
}