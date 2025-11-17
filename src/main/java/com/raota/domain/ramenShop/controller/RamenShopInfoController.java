package com.raota.domain.ramenShop.controller;

import com.raota.domain.ramenShop.controller.request.RamenShopSearchRequest;
import com.raota.global.common.ApiResponse;
import com.raota.domain.ramenShop.controller.request.VisitCertificationRequest;
import com.raota.domain.ramenShop.controller.response.RamenShopBasicInfoResponse;
import com.raota.domain.ramenShop.controller.response.VisitCountingResponse;
import com.raota.domain.ramenShop.controller.response.VotingStatusResponse;
import com.raota.domain.ramenShop.controller.response.WaitingSpotResponse;
import com.raota.domain.ramenShop.controller.response.StoreSummaryResponse;
import com.raota.domain.ramenShop.service.RamenShopInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ramen-shops")
@RequiredArgsConstructor
public class RamenShopInfoController {

    private final RamenShopInfoService ramenShopInfoService;

    @PostMapping("/{shopId}/visit")
    public ResponseEntity<ApiResponse<VisitCountingResponse>> addVisitCount(
            @PathVariable Long shopId,
            @RequestBody VisitCertificationRequest request
    ) {
        VisitCountingResponse response = ramenShopInfoService.addVisitCount(shopId, request.getUserId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{shopId}/votes/{menuId}")
    public ResponseEntity<ApiResponse<VotingStatusResponse>> getVoteStatus(
            @PathVariable Long shopId,
            @PathVariable Long menuId) {
        VotingStatusResponse response = ramenShopInfoService.voteTheMenu(shopId, menuId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{shopId}/nearby")
    public ResponseEntity<ApiResponse<WaitingSpotResponse>> getNearByPlace(@PathVariable Long shopId) {
        WaitingSpotResponse response = ramenShopInfoService.getWaitingSpot(shopId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ApiResponse<RamenShopBasicInfoResponse>> getShopDetailInfo(@PathVariable Long shopId) {
        RamenShopBasicInfoResponse response = ramenShopInfoService.getShopDetailInfo(shopId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<StoreSummaryResponse>>> getShopDetailInfo(
            @PageableDefault(size = 12, direction = Sort.Direction.DESC) Pageable pageable,
            RamenShopSearchRequest request) {
        Page<StoreSummaryResponse> response = ramenShopInfoService.getRamenShopList(request, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
