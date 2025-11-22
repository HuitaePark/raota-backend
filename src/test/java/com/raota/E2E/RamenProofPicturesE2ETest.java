package com.raota.E2E;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RamenProofPicturesE2ETest extends BaseE2ETest {

    private static final long TEST_SHOP_ID = 1L;

    @Test
    @DisplayName("E2E: 라멘집 인증샷 목록 조회")
    void get_shop_photos() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("shopId", TEST_SHOP_ID)
                .param("page", 0)
                .param("size", 10)

                .when()
                .get("/photos/{shopId}", TEST_SHOP_ID)

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data", notNullValue())
                .body("data.number", equalTo(0))
                .body("data.size", equalTo(10))
                .body("data.totalElements", greaterThanOrEqualTo(0));
    }
}
