package com.raota.controller;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.ramenShop.controller.RamenShopInfoController;
import com.raota.ramenShop.controller.response.VisitCountingResponse;
import com.raota.ramenShop.controller.response.VotingStatusResponse;
import com.raota.ramenShop.controller.response.WaitingSpotResponse;
import com.raota.ramenShop.dto.VoteResultsDto;
import com.raota.ramenShop.dto.WaitingSpotDto;
import com.raota.ramenShop.service.RamenShopInfoService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RamenShopInfoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RamenShopInfoController ramenShopInfoController; // Mock 주입 대상

    @Mock
    private RamenShopInfoService ramenShopInfoService; // Mockito Mock

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ramenShopInfoController).build();
    }

    @DisplayName("방문 인증이 되면 횟수 추가를 하고 결과 정보를 받는다.")
    @Test
    void add_the_count_and_receive_the_results() throws Exception {
        Long shopId = 1L;
        Long userId = 123L;
        VisitCountingResponse visitCountingResponse = new VisitCountingResponse(
                1L,
                123L,
                1251,
                "방문이 인증되었습니다.");
        given(ramenShopInfoService.addVisitCount(shopId, userId)).willReturn(visitCountingResponse);

        mockMvc.perform(post("/ramen-shops/{shopId}/visit", shopId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":123}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.restaurant_id").value(1))
                .andExpect(jsonPath("$.data.user_id").value(123))
                .andExpect(jsonPath("$.data.new_visit_count").value(1251))
                .andExpect(jsonPath("$.data.message").value("방문이 인증되었습니다."));
    }

    @DisplayName("투표 현황을 조회한다.")
    @Test
    void view_voting_status() throws Exception {
        Long shopId = 1L;
        VotingStatusResponse response = new VotingStatusResponse(620,
                List.of(new VoteResultsDto(1L, "시오라멘", 410, 66.1)));
        given(ramenShopInfoService.getVotingStatus(shopId)).willReturn(response);

        mockMvc.perform(get("/ramen-shops/{shopId}/votes", shopId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.total_votes").value(620))
                // 배열 값 확인
                .andExpect(jsonPath("$.data.vote_results[0].menu_id").value(1))
                .andExpect(jsonPath("$.data.vote_results[0].menu_name").value("시오라멘"))
                .andExpect(jsonPath("$.data.vote_results[0].vote_count").value(410))
                .andExpect(jsonPath("$.data.vote_results[0].percentage").value(66.1));
    }

    @DisplayName("투표 후 바뀐 투표 현황을 확인한다.")
    @Test
    void view_voting_status_after_vote() throws Exception {
        Long shopId = 1L;
        Long menuId = 1L;
        VotingStatusResponse response = new VotingStatusResponse(620,
                List.of(new VoteResultsDto(1L, "시오라멘", 410, 66.1)));
        given(ramenShopInfoService.voteTheMenu(shopId, menuId)).willReturn(response);

        mockMvc.perform(post("/ramen-shops/{shopId}/votes/{menuId}", shopId, menuId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.total_votes").value(620))
                // 배열 값 확인
                .andExpect(jsonPath("$.data.vote_results[0].menu_id").value(1))
                .andExpect(jsonPath("$.data.vote_results[0].menu_name").value("시오라멘"))
                .andExpect(jsonPath("$.data.vote_results[0].vote_count").value(410))
                .andExpect(jsonPath("$.data.vote_results[0].percentage").value(66.1));
    }

    @DisplayName("주변 대기장소 목록을 조회한다.")
    @Test
    void get_nearBy_waiting_spot() throws Exception {
        Long shopId = 1L;
        WaitingSpotResponse response = new WaitingSpotResponse(List.of(new WaitingSpotDto(
                "cafe_123",
                "스타벅스 홍대역점",
                "카페",
                410,
                "서울 마포구 양화로 123",
                "https://cdn.menschelin.com/images/places/starbucks_hongdae.jpg")));
        given(ramenShopInfoService.getWaitingSpot(shopId)).willReturn(response);

        mockMvc.perform(get("/ramen-shops/{shopId}/nearby", shopId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                // 배열 값 확인
                .andExpect(jsonPath("$.data.nearby_places[0].place_id").value("cafe_123"))
                .andExpect(jsonPath("$.data.nearby_places[0].name").value("스타벅스 홍대역점"))
                .andExpect(jsonPath("$.data.nearby_places[0].category").value("카페"))
                .andExpect(jsonPath("$.data.nearby_places[0].distance_meters").value(410))
                .andExpect(jsonPath("$.data.nearby_places[0].address").value("서울 마포구 양화로 123"))
                .andExpect(jsonPath("$.data.nearby_places[0].image_url").value(
                        "https://cdn.menschelin.com/images/places/starbucks_hongdae.jpg"));
    }
}
