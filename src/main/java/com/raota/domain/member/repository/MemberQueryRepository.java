package com.raota.domain.member.repository;

import com.raota.domain.member.controller.response.MyProfileResponse;

public interface MemberQueryRepository {
    MyProfileResponse findMemberDetailInfo(Long memberId);
}
