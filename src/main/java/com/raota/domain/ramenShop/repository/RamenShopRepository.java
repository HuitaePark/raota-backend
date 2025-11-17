package com.raota.domain.ramenShop.repository;

import com.raota.domain.ramenShop.controller.response.RamenShopProofPictureResponse;
import com.raota.domain.ramenShop.controller.response.StoreSummaryResponse;
import com.raota.domain.ramenShop.model.RamenShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RamenShopRepository extends JpaRepository<RamenShop, Long> {

    @Query(
            value = """
                    select new com.raota.domain.ramenShop.controller.response.StoreSummaryResponse(
                        s.id,
                        s.name,
                        concat(s.address.city, concat(' ', s.address.district)),
                        s.tags,
                        s.imageUrl
                    )
                    from RamenShop s
                    where (:region is null or concat(s.address.city, concat(' ', s.address.district)) = :region)
                      and (:keyword is null or s.name like concat('%', :keyword, '%'))
                    """,
            countQuery = """
                    select count(s)
                    from RamenShop s
                    where (:region is null or concat(s.address.city, concat(' ', s.address.district)) = :region)
                      and (:keyword is null or s.name like concat('%', :keyword, '%'))
                    """
    )
    Page<StoreSummaryResponse> searchStores(@Param("region") String region,
                                            @Param("keyword") String keyword,
                                            Pageable pageable);

    @Query(value = """
        select new com.raota.domain.ramenShop.controller.response.RamenShopProofPictureResponse(
            p.id,
            p.imageUrl,
            p.memberProfile.nickname,
            p.uploadAt
        )
        from RamenProofPicture p
        where p.ramenShop.id = :shopId
        order by p.uploadAt desc
        """,
            countQuery = """
        select count(p)
        from RamenProofPicture p
        where p.ramenShop.id = :shopId
        """)
    Page<RamenShopProofPictureResponse> searchPictures(@Param("shopId") Long shopId,Pageable pageable);
}
