package com.raota.ramenShop.service;

import com.raota.ramenShop.controller.response.VisitCountingResponse;
import com.raota.ramenShop.controller.response.VotingStatusResponse;
import org.springframework.stereotype.Service;

@Service
public class RamenShopInfoService {

    public VisitCountingResponse addVisitCount(Long shopId, Long userId) {
        return null;
    }

    public VotingStatusResponse getVotingStatus(Long shopId) {
        return null;
    }

    public VotingStatusResponse voteTheMenu(Long shopId, Long menuId) {
        return null;
    }
}