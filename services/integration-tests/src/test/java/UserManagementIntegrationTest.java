import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserManagementIntegrationTest {

    private static final String BASE_URI = "http://localhost:4004/auth";
    private static final String CREATION_EMAIL = emailGenerationMethod();
    private static final String CREATION_PASSWORD = "password";
    private static final String OLD_PASSWORD = "password1234";
    private static final String NEW_PASSWORD = "password12345";
    private static final String ROLE = "ADMIN";

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @Order(1)
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO(CREATION_EMAIL, CREATION_PASSWORD, ROLE);

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("email", equalTo(CREATION_EMAIL))
                .body("role", equalTo(ROLE))
                .body("status", equalTo("ACTIVE"));
    }

    @Test
    @Order(2)
    @DisplayName("Should update user password")
    void shouldUpdateUserPassword(){
        Long userId = 1L;
        UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO = new UpdateUserPasswordRequestDTO(OLD_PASSWORD, NEW_PASSWORD);

        given()
                .contentType(ContentType.JSON)
                .body(updateUserPasswordRequestDTO)
                .when()
                .put("/users/{userId}/password", userId)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    @DisplayName("Should disable user")
    void shouldDisableUser(){
        Long userId = 1L;

        given()
                .when()
                .put("/users/{userId}/disable", userId)
                .then()
                .statusCode(200)
                .body("status", equalTo("INACTIVE"));
    }

    @Test
    @Order(4)
    @DisplayName("Should enable user")
    void shouldEnableUser(){
        Long userId = 1L;

        given()
                .when()
                .put("/users/{userId}/enable", userId)
                .then()
                .statusCode(200)
                .body("status", equalTo("ACTIVE"));
    }

    static String emailGenerationMethod(){
        double prefix = Math.random();
        String username = "user";
        String domain = "@hsm.com";


        return prefix +
                username +
                domain;

    }

    private record CreateUserRequestDTO(String email, String password, String role) {}
    private record UpdateUserPasswordRequestDTO(String currentPassword, String newPassword) {}
}
