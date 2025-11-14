package com.raota.domain.member.repository;

import com.raota.domain.member.controller.response.MyProfileResponse;
import com.raota.domain.member.model.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberProfile,Long> {
    @Query("""
    select new com.raota.domain.member.controller.response.MyProfileResponse(
        m.id,
        m.nickname,
        m.imageUrl,
        new com.raota.domain.member.dto.UserStatsDto(
            m.memberActivityStats.visitedRestaurantCount,
            m.memberActivityStats.photoCount,
            m.memberActivityStats.bookmarkCount
        )
    )
    from MemberProfile m
    where m.id = :id
    """)
    MyProfileResponse findMemberDetailInfo(Long id);
}
