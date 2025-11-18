package com.raota.domain.member.service;

import com.raota.domain.member.controller.response.BookmarkSummaryResponse;
import com.raota.domain.member.controller.response.MyProfileResponse;
import com.raota.domain.member.controller.response.PhotoSummaryResponse;
import com.raota.domain.member.controller.response.VisitSummaryResponse;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberInfoService {

    private final MemberRepository memberRepository;

    public MyProfileResponse getMyProfile(Long memberId) {
        return memberRepository.findMemberDetailInfo(memberId);
    }

    public MyProfileResponse updateMyProfile(String updateNickname,String updateImage,Long memberId) {
        MemberProfile member = memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("없는 유저 정보 입니다."));

        member.updateProfile(updateNickname,updateNickname);
        memberRepository.save(member);

        return getMyProfile(memberId);
    }

    public Page<PhotoSummaryResponse> getMyPhotoList(Pageable pageable){
        return null;
    }

    public Page<BookmarkSummaryResponse> getMyBookmarks(Pageable pageable) {
        return null;
    }

    public Page<VisitSummaryResponse> getMyVisits(Pageable pageable) {
        return null;
    }
}
