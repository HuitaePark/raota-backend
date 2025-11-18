package com.raota.domain.proofPicture.service;

import com.raota.domain.proofPicture.controller.response.ProofPictureInfoResponse;
import com.raota.domain.proofPicture.repository.RamenProofPictureRepository;
import com.raota.domain.proofPicture.controller.response.RamenShopProofPictureResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class RamenProofPictureService {

    private final RamenProofPictureRepository proofPictureRepository;

    public ProofPictureInfoResponse addProofPicture(Long photoId, MultipartFile file) {
        return null;
    }

    public Page<RamenShopProofPictureResponse> findProofPicture(Long shopId, Pageable pageable) {
        return proofPictureRepository.searchPictures(shopId,pageable);
    }
}
