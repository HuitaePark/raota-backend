
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseE2ETest {

    @BeforeAll
    static void setupRestAssured() {
        String baseUri = System.getProperty("e2e.base-uri", "http://localhost");
        String portProp = System.getProperty("e2e.port", "8080");

        RestAssured.baseURI = baseUri;
        RestAssured.port = Integer.parseInt(portProp);
    }
}