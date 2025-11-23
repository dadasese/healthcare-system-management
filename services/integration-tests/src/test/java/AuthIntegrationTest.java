
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest extends BaseIntegrationTest{

    private static final String INVALID_EMAIL = "wrongcredential@hsm.com";
    private static final String INVALID_PASSWORD = "wrongcredential";
    private static final String AUTH_SERVICE_LOGIN_URI = "/auth/login";
    private static final String AUTH_SERVICE_VALIDATE_URI = "/auth/validate";
    @Test
    @Order(1)
    @DisplayName("Should login successfully with valid credentials")
     void shouldLoginSuccessfullyWithValidCredentials(){
        LoginRequestDTO loginRequest = new LoginRequestDTO(ADMIN_EMAIL, ADMIN_PASSWORD);
        Response response = given()
                .contentType("application/json")
                .body(loginRequest)
                .when()
                .post(AUTH_SERVICE_LOGIN_URI)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("token", notNullValue())
                .body( "generatedAt", notNullValue())
                .time(lessThan(3000L))
                .extract()
                .response();

        String token = response.jsonPath().getString("token");
        Assertions.assertNotNull(token, "Token should not be null");
        Assertions.assertTrue(token.length() > 20, "Token should be properly formatted");
    }

    @Test
    @Order(2)
    @DisplayName("Should return 401 when credentials are invalid")
     void shouldReturnUnauthorizedWithInvalidCredentials(){
        LoginRequestDTO loginRequest = new LoginRequestDTO(INVALID_EMAIL, INVALID_PASSWORD);

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post(AUTH_SERVICE_LOGIN_URI)
                .then()
                .statusCode(401);

    }

    @Test
    @Order(3)
    @DisplayName("Should validate token successfully")
    void shouldValidateTokenSuccessfully(){
        String token = getAdminToken();

        given()
                .header("Authorization", TokenHelper.bearerToken(token))
                .when()
                .get(AUTH_SERVICE_VALIDATE_URI)
                .then()
                .statusCode(200);

    }

    @Test
    @Order(4)
    @DisplayName("Should return 401 for invalid token")
    void shouldReturnUnauthorizedForInvalidToken(){
        given()
                .header("Authorization", "Bearer invalid.token.here")
                .when()
                .get(AUTH_SERVICE_VALIDATE_URI)
                .then()
                .statusCode(401);
    }


    private record LoginRequestDTO(String email, String password) {}
}
