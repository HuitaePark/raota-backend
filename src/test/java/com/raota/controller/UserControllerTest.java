package com.raota.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.domain.user.controller.UserController;
import com.raota.domain.user.dto.UserStatsDto;
import com.raota.domain.user.controller.response.MyProfileResponse;
import com.raota.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @DisplayName("유저의 닉네임, 프로필 이미지, 칭호, 방문 통계등의 기본 정보를 제공한다.")
    @Test
    void Provide_basic_information_about_the_user() throws Exception {
        var stats = new UserStatsDto(
                12,
                3,
                4,
                2
        );

        var response = new MyProfileResponse(
                201L,
                "이스프린",
                "https://cdn.menschelin.com/images/user/201/profile.jpg",
                stats
        );

        given(userService.getMyProfile()).willReturn(response);

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
                .andExpect(jsonPath("$.data.stats.total_visit_count").value(3))
                .andExpect(jsonPath("$.data.stats.total_photo_count").value(4))
                .andExpect(jsonPath("$.data.stats.total_bookmark_count").value(2));
    }
}
