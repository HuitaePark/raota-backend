package com.raota.domain.user.controller;

import com.raota.domain.user.controller.request.UpdateProfileRequest;
import com.raota.domain.user.controller.response.BookmarkSummaryResponse;
import com.raota.domain.user.controller.response.MyProfileResponse;
import com.raota.domain.user.controller.response.PhotoSummaryResponse;
import com.raota.domain.user.service.UserInfoService;
import com.raota.global.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/me/profile")
    public ResponseEntity<ApiResponse<MyProfileResponse>> getUserProfile(){
        return ResponseEntity.ok(ApiResponse.success(userInfoService.getMyProfile()));
    }

    @PatchMapping("/me/profile")
    public ResponseEntity<ApiResponse<MyProfileResponse>> updateMyProfile(
            @RequestBody UpdateProfileRequest request
    ) {
        MyProfileResponse updated = userInfoService.updateMyProfile(request);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @GetMapping("/me/photos")
    public ResponseEntity<ApiResponse<Page<PhotoSummaryResponse>>> getUserPhoto(Pageable pageable){
        Page<PhotoSummaryResponse> photos = userInfoService.getMyPhotoList(pageable);
        return ResponseEntity.ok(ApiResponse.success(photos));
    }

    @GetMapping("/me/bookmarks")
    public ResponseEntity<ApiResponse<Page<BookmarkSummaryResponse>>> getMyBookmarks(Pageable pageable) {
        Page<BookmarkSummaryResponse> page = userInfoService.getMyBookmarks(pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }
}
