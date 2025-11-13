package com.raota.domain.member.controller.response;

public record PhotoSummaryResponse(
        Long photo_id,
        String image_url,
        Long restaurant_id,
        String restaurant_name,
        String uploaded_at
) {
}
