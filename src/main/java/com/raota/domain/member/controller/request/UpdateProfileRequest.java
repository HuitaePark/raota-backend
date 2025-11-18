package com.raota.domain.member.controller.request;

import lombok.Getter;

@Getter
public class UpdateProfileRequest {
    private String nickname;
    private String profile_image_url;
}
