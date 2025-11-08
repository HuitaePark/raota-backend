package com.raota.controller;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.ramenShop.controller.RamenShopInfoController;
import com.raota.ramenShop.controller.response.VisitCountingResponse;
import com.raota.ramenShop.service.RamenShopInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RamenShopInfoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RamenShopInfoController ramenShopInfoController; // Mock 주입 대상

    @Mock
    private RamenShopInfoService ramenShopInfoService; // Mockito Mock

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ramenShopInfoController).build();
    }

    @DisplayName("방문 인증이 되면 횟수 추가를 하고 결과 정보를 받는다.")
    @Test
    void add_the_count_and_receive_the_results() throws Exception {
        Long shopId = 1L;
        Long userId = 123L;
        VisitCountingResponse visitCountingResponse = new VisitCountingResponse(1L,123L,1251,"방문이 인증되었습니다.");
        given(ramenShopInfoService.addViisitiCount(shopId,userId)).willReturn(visitCountingResponse);

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
}
