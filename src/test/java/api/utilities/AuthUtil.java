package api.utilities;

import com.github.javafaker.Faker;

import api.endpoints.AuthEndpoints;
import api.payload.AuthPayload;
import io.restassured.response.Response;

public class AuthUtil {
	public static String getToken() {

        Faker faker = new Faker();

        // 🔹 Create payload
        AuthPayload payload = new AuthPayload();
        payload.setEmail(faker.internet().emailAddress());

        String password = faker.name().firstName() + "123";
        payload.setPassword(password);

        // 🔹 Step 1: Register
        AuthEndpoints.register_user(payload);

        // 🔹 Step 2: Login with SAME payload
        Response response = AuthEndpoints.login_user(payload);

        return response.jsonPath().getString("token");
    }

}
