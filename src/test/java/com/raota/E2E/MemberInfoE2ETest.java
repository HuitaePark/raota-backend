package com.raota.E2E;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberInfoE2ETest extends BaseE2ETest {

    private static final long TEST_MEMBER_ID = 1L;

    @Test
    @DisplayName("E2E: 내 프로필 조회")
    void get_my_profile() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("X-User-Id", TEST_MEMBER_ID)

                .when()
                .get("/users/me/profile")

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data", notNullValue())
                .body("data.user_id", equalTo((int) TEST_MEMBER_ID));
    }

    @Test
    @DisplayName("E2E: 내 프로필 수정")
    void update_my_profile() {
        String newNickname = "E2E-테스트-유저";
        String newImage = "https://e2e-test-image.com/user1";

        String requestBody = """
            {
              "nickname": "%s",
              "profile_image_url": "%s"
            }
            """.formatted(newNickname, newImage);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("X-User-Id", TEST_MEMBER_ID)
                .body(requestBody)

                .when()
                .patch("/users/me/profile")

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.nickname", equalTo(newNickname))
                .body("data.profile_image_url", equalTo(newImage));
    }

    @Test
    @DisplayName("E2E: 내가 사진 남긴 레스토랑 목록을 조회한다.")
    void get_my_visited_restaurant_list() {
        RestAssured.given().log().all()
                .param("page", 0)
                .param("size", 10)
                .contentType(ContentType.JSON)
                .header("X-User-Id", TEST_MEMBER_ID)

                .when()
                .get("/users/me/visits")

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data.totalElements", greaterThanOrEqualTo(0))
                .body("data.content", notNullValue())
                .body("data.number", equalTo(0))
                .body("data.size", equalTo(10))

                .body("data.content[0].restaurant_name", notNullValue())
                .body("data.content[0].restaurant_id", notNullValue())
                .body("data.content[0].restaurant_image_url", anything())
                .body("data.content[0].simple_address", notNullValue());
    }

    @Test
    @DisplayName("E2E: 내 사진 목록 조회")
    void get_my_photo_list() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("X-User-Id", TEST_MEMBER_ID)
                .param("page", 0)
                .param("size", 10)

                .when()
                .get("/users/me/photos")

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data", notNullValue())
                .body("data.number", equalTo(0))
                .body("data.size", equalTo(10));
    }

    @Test
    @DisplayName("E2E: 내 북마크 목록 조회")
    void get_my_bookmarks() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("X-User-Id", TEST_MEMBER_ID)
                .param("page", 0)
                .param("size", 10)

                .when()
                .get("/users/me/bookmarks")

                .then().log().all()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("data", notNullValue())
                .body("data.number", equalTo(0))
                .body("data.size", equalTo(10))
                .body("data.totalElements", greaterThanOrEqualTo(0));
    }
}