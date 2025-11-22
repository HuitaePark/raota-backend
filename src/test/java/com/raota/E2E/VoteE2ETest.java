package com.raota.E2E;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VoteE2ETest extends BaseE2ETest {

    private static final long TEST_SHOP_ID = 1L;
    private static final long TEST_MENU_ID = 1L;
    private static final long TEST_MEMBER_ID = 1L;

    @Test
    @DisplayName("E2E: 메뉴 투표 → 투표 수 증가 확인")
    void vote_menu_and_check_status() {
        //Given 현재 투표 수 조회
        int beforeTotalVotes =
                RestAssured.given().log().all()
                        .contentType(ContentType.JSON)
                        .pathParam("shopId", TEST_SHOP_ID)
                        .when()
                        .get("/votes/{shopId}", TEST_SHOP_ID)
                        .then().log().all()
                        .statusCode(200)
                        .body("status", equalTo("SUCCESS"))
                        .body("data.total_votes", greaterThanOrEqualTo(0))
                        .extract()
                        .path("data.total_votes");

        //When 특정 메뉴에 투표
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("shopId", TEST_SHOP_ID)
                .pathParam("menuId", TEST_MENU_ID)
                .header("X-User-Id", TEST_MEMBER_ID)

                .when()
                .post("/votes/{shopId}/menus/{menuId}", TEST_SHOP_ID, TEST_MENU_ID)

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.total_votes", greaterThanOrEqualTo(beforeTotalVotes + 1));

        //Then 다시 상태 조회해서 total_votes가 증가했는지 확인
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("shopId", TEST_SHOP_ID)

                .when()
                .get("/votes/{shopId}", TEST_SHOP_ID)

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.total_votes", Matchers.greaterThan(beforeTotalVotes));
    }
}