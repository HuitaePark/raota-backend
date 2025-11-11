package com.raota.ramenShop.service;

import com.raota.ramenShop.controller.response.RamenShopBasicInfoResponse;
import com.raota.ramenShop.controller.response.VisitCountingResponse;
import com.raota.ramenShop.controller.response.VotingStatusResponse;
import com.raota.ramenShop.controller.response.WaitingSpotResponse;
import com.raota.ramenShop.dto.StoreSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public WaitingSpotResponse getWaitingSpot(Long shopId) {
        return null;
    }

    public RamenShopBasicInfoResponse getShopDetailInfo(Long shopId) {
        return null;
    }

    public Page<StoreSummaryResponse> getRamenShopList(Pageable pageable) {
        return null;
    }
}