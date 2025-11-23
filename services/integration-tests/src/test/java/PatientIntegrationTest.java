import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest extends BaseIntegrationTest{

    private static final String NAME = "John";
    private static final String LASTNAME = "Doe";
    private static final String PHONE_NUMBER = "3208817429";
    private static final String ADDRESS = "Wall Street 21";
    private static final String EMAIL = emailGenerationMethod();
    private static final String BIRTHDATE = "2001-01-02";
    private static final String NAME_UPDATED = "Diane";
    private static final String LASTNAME_UPDATED = "Redmine";
    private static final String PHONE_NUMBER_UPDATED = "3424234";
    private static final String ADDRESS_UPDATED = "Manhattan Street 21";
    private static final String EMAIL_UPDATED = emailGenerationMethod();
    private static final String BIRTHDATE_UPDATED = "2001-01-02";
    private static final String PATIENT_SERVICE_URI = "/api/v1/patients";

    @Test
    @Order(1)
    @DisplayName("Should return patients with authorization")
    void shouldReturnPatientsWithAuthorization() {

        String token = getAdminToken();

            given()
                    .header("Authorization", TokenHelper.bearerToken(token))
                    .when()
                    .get(PATIENT_SERVICE_URI)
                    .then()
                    .statusCode(200)
                    .body("patients", notNullValue());
    }

    @Test
    @Order(2)
    @DisplayName("Should create patients with authorization")
    void shouldCreatePatientsWithAuthorization(){

        CreatePatientDTO createPatientDTO = new CreatePatientDTO(NAME, LASTNAME, PHONE_NUMBER, ADDRESS, EMAIL, BIRTHDATE);

        String token = getAdminToken();

        given()
                .header("Authorization", TokenHelper.bearerToken(token))
                .contentType(ContentType.JSON)
                .body(createPatientDTO)
                .when()
                .post(PATIENT_SERVICE_URI)
                .then()
                .statusCode(200)
                .extract().response();

    }

    @Test
    @Order(3)
    @DisplayName("Should update a patient with authorization")
    void shouldUpdatePatientWithAuthorization(){
        UpdatePatientDTO updatePatientDTO = new UpdatePatientDTO(NAME_UPDATED, LASTNAME_UPDATED, PHONE_NUMBER_UPDATED, ADDRESS_UPDATED, EMAIL_UPDATED, BIRTHDATE_UPDATED);

        String token = getAdminToken();

        long PATIENT_ID = 7L;
        given().header("authorization", TokenHelper.bearerToken(token))
                .contentType(ContentType.JSON)
                .body(updatePatientDTO)
                .when()
                .put(PATIENT_SERVICE_URI + "/" + PATIENT_ID)
                .then()
                .statusCode(200);
    }

    static String emailGenerationMethod(){
        double prefix = Math.random();
        String username = "patient";
        String domain = "@gmail.com";


        return prefix +
                username +
                domain;

    }


    record CreatePatientDTO(String name, String lastName, String phoneNumber, String address, String email, String birthDate){}
    record UpdatePatientDTO(String name, String lastName, String phoneNumber, String address, String email, String birthDate){}
}
