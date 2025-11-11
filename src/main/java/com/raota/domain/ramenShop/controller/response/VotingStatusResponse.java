package com.raota.domain.ramenShop.controller.response;

import com.raota.domain.ramenShop.dto.VoteResultsDto;
import java.util.List;

public record VotingStatusResponse (
        int total_votes,
        List<VoteResultsDto> vote_results
){
}
