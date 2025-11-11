package com.raota.domain.user.controller.response;


import com.raota.domain.user.dto.UserStatsDto;

public record MyProfileResponse (
        Long user_id,
        String nickname,
        String profile_image_url,
        UserStatsDto stats
){
}
