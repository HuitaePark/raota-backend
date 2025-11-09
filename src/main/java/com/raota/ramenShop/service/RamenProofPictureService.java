package com.raota.ramenShop.service;

import com.raota.ramenShop.controller.response.ProofPictureInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RamenProofPictureService {
    public ProofPictureInfoResponse addProofPicture(Long photoId, MultipartFile file) {
        return null;
    }

    public Page<ProofPictureInfoResponse> findProofPicture(Long photoId, Pageable pageable) {
        return null;
    }
}
