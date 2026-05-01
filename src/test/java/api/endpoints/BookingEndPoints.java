package api.endpoints;

import static io.restassured.RestAssured.*;

import api.payload.BookingPayload;
import io.restassured.response.Response;

public class BookingEndPoints {

    // 1. Create booking
    public static Response create_booking(BookingPayload payload, String bearer_token) {

        Response response = given()
                .headers("Authorization", "Bearer " + bearer_token)
                .contentType("application/json")
                .accept("application/json")
                .body(payload)

                .when()
                .post(Roots.create_booking_url);

        return response;
    }

    // 2. Read booking by ID
    public static Response read_booking(int bookingId, String bearer_token) {

        Response response = given()
                .headers("Authorization", "Bearer " + bearer_token)
                .contentType("application/json")
                .accept("application/json")
                .pathParam("bookingId", bookingId)

                .when()
                .get(Roots.read_booking_url);

        return response;
    }

    // 3. Read booking by Reference
    public static Response read_booking_by_ref(String bookingRef, String bearer_token) {

        Response response = given()
                .headers("Authorization", "Bearer " + bearer_token)
                .contentType("application/json")
                .accept("application/json")
                .pathParam("bookingRef", bookingRef)

                .when()
                .get(Roots.read_bookingByReference_url);

        return response;
    }

    // 4. Delete booking
    public static Response delete_booking(int bookingId, String bearer_token) {

        Response response = given()
                .headers("Authorization", "Bearer " + bearer_token)
                .contentType("application/json")
                .accept("application/json")
                .pathParam("bookingId", bookingId)

                .when()
                .delete(Roots.deleteBook_url);

        return response;
    }
}