package com.raota.domain.member.controller.response;

import java.time.LocalDateTime;

public record VisitSummaryResponse(
        Long restaurant_id,
        String restaurant_name,
        String restaurant_image_url,
        String city,
        String district,
        long visit_count_for_user,
        LocalDateTime last_visited_at
) {
    public String address_simple() {
        return city + " " + district;
    }
}