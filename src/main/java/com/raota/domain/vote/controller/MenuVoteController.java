package com.raota.domain.vote.controller;

import com.raota.domain.ramenShop.controller.response.VotingStatusResponse;
import com.raota.domain.vote.service.MenuVoteService;
import com.raota.global.auth.LoginMember;
import com.raota.global.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/votes")
public class MenuVoteController {

    private final MenuVoteService menuVoteService;

    @GetMapping("/{shopId}")
    public ResponseEntity<ApiResponse<VotingStatusResponse>> getVoteStatus(@PathVariable Long shopId) {
        VotingStatusResponse response = menuVoteService.getVotingStatus(shopId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{shopId}/menus/{menuId}")
    public ResponseEntity<ApiResponse<VotingStatusResponse>> getVoteStatus(
            @PathVariable Long shopId,
            @PathVariable Long menuId,
            @LoginMember Long memberId) {
        VotingStatusResponse response = menuVoteService.voteTheMenu(shopId, menuId,memberId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
