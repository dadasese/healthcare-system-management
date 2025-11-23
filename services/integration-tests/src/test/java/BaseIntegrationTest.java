import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseIntegrationTest {

    protected static final String BASE_URI = "http://localhost:4004";
    protected static final String ADMIN_EMAIL = "admin@hsm.com";
    protected static final String ADMIN_PASSWORD = "password12345";

    @BeforeAll
    static void setUpBase(){
        RestAssured.baseURI = BASE_URI;
    }

    @AfterAll
    static void tearDownBase(){
        TokenHelper.clearCache();
    }
    /**
     * Helper method to get admin token
     */
    protected String getAdminToken(){
        return TokenHelper.getAdminToken();
    }
}
