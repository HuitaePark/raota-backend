package com.raota.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.domain.ramenShop.controller.response.VotingStatusResponse;
import com.raota.domain.ramenShop.dto.VoteResultsDto;
import com.raota.domain.vote.controller.MenuVoteController;
import com.raota.domain.vote.service.MenuVoteService;
import java.util.List;
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
public class VoteControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MenuVoteController controller;

    @Mock
    private MenuVoteService menuVoteService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @DisplayName("투표 현황을 조회한다.")
    @Test
    void view_voting_status() throws Exception {
        Long shopId = 1L;
        VotingStatusResponse response = new VotingStatusResponse(
                620,
                List.of(new VoteResultsDto(1L, "시오라멘", 410L, 66.1)));
        given(menuVoteService.getVotingStatus(shopId)).willReturn(response);

        mockMvc.perform(get("/votes/{shopId}", shopId)
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
}
