package com.raota.domain.proofPicture.repository;

import com.raota.domain.proofPicture.controller.response.ProofPictureInfoResponse;
import com.raota.domain.proofPicture.model.RamenProofPicture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RamenProofPictureRepository extends JpaRepository<RamenProofPicture,Long>{
}
