import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenHelper {

    private static final String AUTH_ENDPOINT = "/auth/login";
    private static final Map<String, String> tokenCache = new HashMap<>();
    private static final String AUTH_EMAIL = "admin@hsm.com";
    private static final String AUTH_PASSWORD = "password12345";
    /**
     * Gets a valid token for the given credentials
     * Uses caching to avoid unnecessary login requests
     */
    public static String getToken(String email, String password){
        String cacheKey = email + ":" + password;

        if (tokenCache.containsKey(cacheKey)){
            return tokenCache.get(cacheKey);
        }

        String token = generateToken(email, password);
        tokenCache.put(cacheKey, token);
        return tokenCache.get(cacheKey);
    }
    /**
     * Gets a valid admin token using default admin credentials
     */
    public static String getAdminToken(){
        return getToken(AUTH_EMAIL, AUTH_PASSWORD);
    }
    /**
     * Generates a new token by calling the login endpoint
     */
    public static String generateToken(String email, String password){
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginRequestDTO)
                .when()
                .post(AUTH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.jsonPath().getString("token");
    }
    /**
     * Clears the token cache
     * Useful when tokens need to be refreshed or between test suites
     */
    public static void clearCache(){
        tokenCache.clear();
    }
    /**
     * Creates an Authorization header value with Bearer token
     */
    public static String bearerToken(String token){
        return "Bearer " + token;

    }

    private record LoginRequestDTO(String email, String password){}
}
