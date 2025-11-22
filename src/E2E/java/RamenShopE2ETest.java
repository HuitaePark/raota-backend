
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RamenShopE2ETest extends BaseE2ETest {

    private static final long TEST_SHOP_ID = 1L;

    @Test
    @DisplayName("E2E: 라멘집 상세 조회")
    void get_shop_detail() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("shopId", TEST_SHOP_ID)

                .when()
                .get("/ramen-shops/{shopId}", TEST_SHOP_ID)

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.id", equalTo((int) TEST_SHOP_ID))
                .body("data.name", notNullValue())
                .body("data.address", notNullValue());
    }

    @Test
    @DisplayName("E2E: 라멘집 검색 (region + keyword)")
    void search_shops() {
        RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .param("region", "서울 마포구")
                .param("keyword", "")
                .param("page", 0)
                .param("size", 10)

                .when()
                .get("/ramen-shops")

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.content", notNullValue())
                .body("data.number", equalTo(0))
                .body("data.size", equalTo(10))
                .body("data.totalElements", greaterThanOrEqualTo(0));
    }
}