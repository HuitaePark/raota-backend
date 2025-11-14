package com.raota.domain.member.repository;

import com.raota.domain.member.controller.response.MyProfileResponse;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository{

    private final EntityManager entityManager;

    @Override
    public MyProfileResponse findMemberDetailInfo(Long memberId) {
        return entityManager.createQuery(
                        "select new com.raota.domain.member.controller.response.MyProfileResponse( " +
                                "   m.id, " +
                                "   m.nickname, " +
                                "   m.imageUrl, " +
                                "   new com.raota.domain.member.dto.UserStatsDto( " +
                                "       m.memberActivityStats.visitedRestaurantCount, " +
                                "       m.memberActivityStats.photoCount, " +
                                "       m.memberActivityStats.bookmarkCount" +
                                "   ) " +
                                ") " +
                                "from MemberProfile m " +
                                "where m.id = :id",
                        MyProfileResponse.class)
                .setParameter("id", memberId)
                .getSingleResult();
    }
}
