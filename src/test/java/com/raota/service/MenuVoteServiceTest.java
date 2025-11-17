package com.raota.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.within;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.raota.domain.ramenShop.controller.response.VotingStatusResponse;
import com.raota.domain.ramenShop.dto.VoteResultsDto;
import com.raota.domain.vote.repository.MenuVoteRepository;
import com.raota.domain.vote.service.MenuVoteService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenuVoteServiceTest {

    @Mock
    private MenuVoteRepository menuVoteRepository;

    @InjectMocks
    private MenuVoteService menuVoteService;

    @DisplayName("투표의 퍼센테이지를 계산하여 리스폰스를 반환한다.")
    @Test
    void calculate_percentage(){
        List<VoteResultsDto> mockResults = List.of(
                new VoteResultsDto(10L, "매운라멘", 25L, null),
                new VoteResultsDto(11L, "돈코츠라멘", 25L, null)
        );
        Long shopId = 1L;
        given(menuVoteRepository.findMenuVoteCounts(eq(shopId)))
                .willReturn(mockResults);

        VotingStatusResponse result = menuVoteService.getVotingStatus(shopId);

        assertThat(result.total_votes()).isEqualTo(50L);
        assertThat(result.vote_results().getFirst().getPercentage()).isCloseTo(50.0, within(0.000001));
    }
}
