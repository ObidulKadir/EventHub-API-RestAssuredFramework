package api.utilities;

public class TokenManager {
	private static String token;
	private static long expiryTime;

	public static String getToken() {

		if (token == null || System.currentTimeMillis() > expiryTime) {
			token = AuthUtil.getToken();

			expiryTime = System.currentTimeMillis() + (60 * 60 * 1000);
		}

		return token;
	}

}

/*
A Token Manager is a utility component that handles authentication tokens efficiently in test automation.

It generates a token once (using AuthUtil or login API)
Then reuses the same token across multiple tests
Avoids repeated API calls for login
Improves execution speed and performance

👉 Advanced Token Manager can also:

Check token expiry
Automatically refresh token when expired
Support parallel execution (thread-safe)

In short:
Token Manager = centralized control + caching of auth tokens for faster and stable tests
*/