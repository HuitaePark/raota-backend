package com.raota.ramenShop.controller;

import com.raota.global.common.ApiResponse;
import com.raota.ramenShop.controller.response.ProofPictureInfoResponse;
import com.raota.ramenShop.service.RamenProofPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class RamenProofPictureController {

    private final RamenProofPictureService proofPictureService;

    @PostMapping("/{shopId}")
    public ResponseEntity<ApiResponse<ProofPictureInfoResponse>> addProofPicture(
            @PathVariable Long shopId,
            @RequestPart("file") MultipartFile file
    ) {
        ProofPictureInfoResponse response = proofPictureService.addProofPicture(shopId,file);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ApiResponse<Page<ProofPictureInfoResponse>>> getProofPicture(
            @PathVariable Long shopId,
            @PageableDefault(size = 12, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(proofPictureService.findProofPicture(shopId,pageable)));
    }
}
