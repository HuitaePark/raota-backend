package com.raota.domain.user.controller.response;

public record VisitSummaryResponse(
        Long restaurant_id,
        String restaurant_name,
        String restaurant_image_url,
        String address_simple,
        int visit_count_for_user,
        String last_visited_at) {
}
