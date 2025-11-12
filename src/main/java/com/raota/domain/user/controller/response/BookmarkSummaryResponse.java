package com.raota.domain.user.controller.response;

public record BookmarkSummaryResponse(
        Long restaurant_id,
        String restaurant_name,
        String restaurant_image_url,
        String address_simple,
        String bookmarked_at) {
}
