package com.raota.domain.proofPicture.repository;

import com.raota.domain.proofPicture.controller.response.ProofPictureInfoResponse;
import com.raota.domain.proofPicture.model.RamenProofPicture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RamenProofPictureRepository extends JpaRepository<RamenProofPicture,Long>{
    @Query(
            value = """
                    select new com.raota.domain.proofPicture.controller.response.ProofPictureInfoResponse(
                        p.id,
                        p.imageUrl,
                        m.nickname,
                        p.uploadAt
                    )
                    from RamenProofPicture p
                    join p.memberProfile m
                    where m.id = :memberId
                    order by p.uploadAt desc
                    """,
            countQuery = """
                    select count(p)
                    from RamenProofPicture p
                    where p.memberProfile.id = :memberId
                    """
    )
    Page<ProofPictureInfoResponse> findMemberUploadPhoto(@Param("memberId") Long memberId, Pageable pageable);
}
