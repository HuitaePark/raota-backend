package com.raota.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.domain.member.controller.MemberInfoController;
import com.raota.domain.member.controller.response.BookmarkSummaryResponse;
import com.raota.domain.member.controller.response.PhotoSummaryResponse;
import com.raota.domain.member.controller.response.VisitSummaryResponse;
import com.raota.domain.member.dto.UserStatsDto;
import com.raota.domain.member.controller.response.MyProfileResponse;
import com.raota.domain.member.service.MemberInfoService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class MemberInfoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MemberInfoController userInfoController;

    @Mock
    private MemberInfoService memberInfoService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userInfoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @DisplayName("유저의 닉네임, 프로필 이미지, 칭호, 방문 통계등의 기본 정보를 제공한다.")
    @Test
    void Provide_basic_information_about_the_user() throws Exception {
        var stats = new UserStatsDto(
                12,
                3,
                4
        );

        var response = new MyProfileResponse(
                201L,
                "이스프린",
                "https://cdn.menschelin.com/images/user/201/profile.jpg",
                stats
        );

        given(memberInfoService.getMyProfile(any())).willReturn(response);

        mockMvc.perform(get("/users/me/profile")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))

                // 상단 유저 정보
                .andExpect(jsonPath("$.data.user_id").value(201))
                .andExpect(jsonPath("$.data.nickname").value("이스프린"))
                .andExpect(jsonPath("$.data.profile_image_url")
                        .value("https://cdn.menschelin.com/images/user/201/profile.jpg"))

                // 통계 정보
                .andExpect(jsonPath("$.data.stats.visited_restaurant_count").value(12))
                .andExpect(jsonPath("$.data.stats.total_photo_count").value(3))
                .andExpect(jsonPath("$.data.stats.total_bookmark_count").value(4));
    }

    @DisplayName("프로필을 수정하면 변경된 프로필 정보를 반환한다.")
    @Test
    void update_my_profile() throws Exception {

        var stats = new UserStatsDto(12, 3, 4);

        var updated = new MyProfileResponse(
                201L,
                "새로운닉네임",
                "https://cdn.menschelin.com/images/user/201/new_profile.jpg",
                stats
        );

        given(memberInfoService.updateMyProfile(any(),any(),any())).willReturn(updated);

        mockMvc.perform(patch("/users/me/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nickname": "새로운닉네임",
                                  "profile_image_url": "https://cdn.menschelin.com/images/user/201/new_profile.jpg"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.user_id").value(201))
                .andExpect(jsonPath("$.data.nickname").value("새로운닉네임"))
                .andExpect(jsonPath("$.data.profile_image_url").value(
                        "https://cdn.menschelin.com/images/user/201/new_profile.jpg"))
                .andExpect(jsonPath("$.data.stats.visited_restaurant_count").value(12));
    }

    @DisplayName("내가 올린 사진 목록을 조회한다.")
    @Test
    void get_my_uploaded_photos() throws Exception {
        var photos = List.of(
                new PhotoSummaryResponse(
                        801L,
                        "https://cdn.menschelin.com/images/user/photo/801.jpg",
                        101L,
                        "켄비멘리키",
                        LocalDateTime.now()
                ),
                new PhotoSummaryResponse(
                        802L,
                        "https://cdn.menschelin.com/images/user/photo/802.jpg",
                        102L,
                        "라멘 스타일 스타일",
                        LocalDateTime.now()
                )
        );

        PageRequest pageable = PageRequest.of(0, 20);
        Page<PhotoSummaryResponse> page = new PageImpl<>(photos, pageable, 4);

        given(memberInfoService.getMyPhotoList(null,pageable)).willReturn(page);

        mockMvc.perform(get("/users/me/photos")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))

                // content 검증
                .andExpect(jsonPath("$.data.content[0].photo_id").value(801))
                .andExpect(jsonPath("$.data.content[0].restaurant_name").value("켄비멘리키"))
                .andExpect(jsonPath("$.data.content[1].photo_id").value(802))
                .andExpect(jsonPath("$.data.content[1].restaurant_id").value(102))

                // Page 메타데이터
                .andExpect(jsonPath("$.data.size").value(20))
                .andExpect(jsonPath("$.data.number").value(0))
                .andExpect(jsonPath("$.data.totalElements").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.first").value(true))
                .andExpect(jsonPath("$.data.last").value(true));
    }

    @DisplayName("내 북마크 목록을 페이징으로 조회한다.")
    @Test
    void get_my_bookmarks() throws Exception {
        var list = List.of(
                new BookmarkSummaryResponse(102L, "라멘 스타일 스타일",
                        "https://cdn.menschelin.com/images/rest/102/main.jpg", "서울 시내",
                        LocalDateTime.now()),
                new BookmarkSummaryResponse(105L, "또다른 라멘집",
                        "https://cdn.menschelin.com/images/rest/105/main.jpg", "서울 강남구",
                        LocalDateTime.now())
        );

        var pageable = PageRequest.of(0, 20);
        var page = new PageImpl<>(list, pageable, 2);

        given(memberInfoService.getMyBookmarks(null,pageable)).willReturn(page);

        mockMvc.perform(get("/users/me/bookmarks")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))

                // content 검증
                .andExpect(jsonPath("$.data.content[0].restaurant_id").value(102))
                .andExpect(jsonPath("$.data.content[0].restaurant_name").value("라멘 스타일 스타일"))
                .andExpect(jsonPath("$.data.content[0].restaurant_image_url").value("https://cdn.menschelin.com/images/rest/102/main.jpg"))
                .andExpect(jsonPath("$.data.content[0].address_simple").value("서울 시내"))

                // 페이지 메타데이터
                .andExpect(jsonPath("$.data.size").value(20))
                .andExpect(jsonPath("$.data.number").value(0))
                .andExpect(jsonPath("$.data.totalElements").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.first").value(true))
                .andExpect(jsonPath("$.data.last").value(true));
    }

    @DisplayName("내 방문 기록을 페이징으로 조회한다.")
    @Test
    void get_my_visits() throws Exception {
        var list = List.of(
                new VisitSummaryResponse(102L, "라멘 스타일 스타일",
                        "https://cdn.menschelin.com/images/rest/102/main.jpg", "서울" ,"시내",
                        3, LocalDateTime.now()),
                new VisitSummaryResponse(101L, "켄비멘리키",
                        "https://cdn.menschelin.com/images/rest/101/main.jpg", "서울","마포구",
                        1, LocalDateTime.now())
        );

        var pageable = PageRequest.of(0, 20);
        var page = new PageImpl<>(list, pageable, 12);

        given(memberInfoService.getMyVisits(null,pageable)).willReturn(page);

        mockMvc.perform(get("/users/me/visits")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))

                // content 검증
                .andExpect(jsonPath("$.data.content[0].restaurant_id").value(102))
                .andExpect(jsonPath("$.data.content[0].restaurant_name").value("라멘 스타일 스타일"))
                .andExpect(jsonPath("$.data.content[0].visit_count_for_user").value(3))
                .andExpect(jsonPath("$.data.content[1].restaurant_id").value(101))
                .andExpect(jsonPath("$.data.content[1].city").value("서울"))

                // 페이지 메타데이터
                .andExpect(jsonPath("$.data.size").value(20))
                .andExpect(jsonPath("$.data.number").value(0))
                .andExpect(jsonPath("$.data.totalElements").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.data.first").value(true))
                .andExpect(jsonPath("$.data.last").value(true));
    }
}
