package com.raota.domain.user.service;

import com.raota.domain.user.controller.request.UpdateProfileRequest;
import com.raota.domain.user.controller.response.BookmarkSummaryResponse;
import com.raota.domain.user.controller.response.MyProfileResponse;
import com.raota.domain.user.controller.response.PhotoSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

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
}
