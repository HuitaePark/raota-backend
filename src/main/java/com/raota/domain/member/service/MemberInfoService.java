package com.raota.domain.member.service;

import com.raota.domain.member.controller.request.UpdateProfileRequest;
import com.raota.domain.member.controller.response.BookmarkSummaryResponse;
import com.raota.domain.member.controller.response.MyProfileResponse;
import com.raota.domain.member.controller.response.PhotoSummaryResponse;
import com.raota.domain.member.controller.response.VisitSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberInfoService {

    public MyProfileResponse getMyProfile() {
        return null;
    }

    public MyProfileResponse updateMyProfile(UpdateProfileRequest request) {
        return null;
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
