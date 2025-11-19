package com.raota.Integration;

import static org.hamcrest.Matchers.equalTo;

import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.member.repository.MemberRepository;
import com.raota.domain.proofPicture.model.RamenProofPicture;
import com.raota.domain.proofPicture.repository.RamenProofPictureRepository;
import com.raota.domain.ramenShop.model.RamenShop;
import com.raota.domain.ramenShop.repository.RamenShopRepository;
import com.raota.global.file.FileUploader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberInfoIntegrationTest {

    @LocalServerPort
    int port;

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

    @Mock
    FileUploader fileUploader;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RamenShopRepository ramenShopRepository;
    @Autowired
    RamenProofPictureRepository pictureRepository;

    private Long memberId;

    @BeforeEach
    void setUp() {
        clearDatabase();
        RestAssured.port = port;

        MemberProfile member = MemberProfile.builder()
                .nickname("바키")
                .stats(MemberActivityStats.init())
                .imageUrl("https://original-image.com")
                .build();

        MemberProfile savedMember = memberRepository.save(member);
        memberId = savedMember.getId();
    }

    private void clearDatabase() {
        memberRepository.deleteAll();
        ramenShopRepository.deleteAll();
        pictureRepository.deleteAll();
    }

    @DisplayName("통합: 유저의 프로필을 조회한다.")
    @Test
    void get_my_profile() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("X-User-Id", memberId)
                .when()
                .get("/users/me/profile")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("status", equalTo("SUCCESS"))
                .body("data.user_id",equalTo(memberId.intValue()))
                .body("data.nickname",equalTo("바키"))
                .body("data.profile_image_url",equalTo("https://original-image.com"))
                .body("data.stats.visited_restaurant_count",equalTo(0));
    }

    @Test
    @DisplayName("통합: 내 프로필 수정 - API 호출 후 실제 DB 값이 변경되어야 한다")
    void update_my_profile() {
        String newNickname = "새로운유저";
        String newImage = "https://new-image.com";

        String requestBody = """
            {
              "nickname": "%s",
              "profile_image_url": "%s"
            }
            """.formatted(newNickname, newImage);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("X-User-Id", memberId)
                .body(requestBody)
                .when()
                .patch("/users/me/profile")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("data.nickname", equalTo(newNickname))
                .body("data.profile_image_url",equalTo(newImage));
    }

    @DisplayName("통합: 멤버의 사진 목록을 조회한다.")
    @Test
    void get_member_photo_list(){
        MemberProfile me = memberRepository.findById(memberId).get();
        RamenShop shop = ramenShopRepository.save(RamenShop.builder().name("사진찍은라멘집").build());

        pictureRepository.save(RamenProofPicture.builder()
                .memberProfile(me)
                .ramenShop(shop)
                .imageUrl("https://photo1.com")
                .build());

        // When
        RestAssured.given().log().all()
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/users/me/photos")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("data.totalElements", equalTo(1))
                .body("data.content[0].restaurant_name", equalTo("사진찍은라멘집"));
    }
}
