package com.raota.domain.vote.service;

import com.raota.domain.ramenShop.controller.response.VotingStatusResponse;
import com.raota.domain.ramenShop.dto.VoteResultsDto;
import com.raota.domain.vote.repository.MenuVoteRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuVoteService {

    private final MenuVoteRepository voteRepository;

    public VotingStatusResponse getVotingStatus(Long shopId) {
        List<VoteResultsDto> statusDto = voteRepository.findMenuVoteCounts(shopId);
        long totalCount = getTotalCount(statusDto);

        statusDto.forEach(dto->{
            double percentage = calculatePercentage(totalCount,dto);
            dto.setPercentage(percentage);
        });

        return new VotingStatusResponse(
                totalCount,
                statusDto
        );
    }

    public VotingStatusResponse voteTheMenu(Long shopId, Long menuId) {
        return null;
    }

    private long getTotalCount(List<VoteResultsDto> statusDto){
        return statusDto.stream()
                .mapToLong(VoteResultsDto::getVote_count)
                .sum();
    }

    private Double calculatePercentage(long totalCount,VoteResultsDto dto){
        return dto.getVote_count()*100.0/totalCount;
    }
}
