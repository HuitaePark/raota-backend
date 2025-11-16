package com.raota.domain.ramenShop.controller.response;

import java.util.List;

public record StoreSummaryResponse(
        Long id,
        String name,
        String address,
        Object tags,
        String imageUrl
        ){
    @SuppressWarnings("unchecked")
    public List<String> tagsAsList() {
        if (tags == null) {
            return List.of();
        }
        if (tags instanceof List<?> list) {
            return (List<String>) list;
        }
        throw new IllegalStateException("tags is not a List: " + tags.getClass());
    }
}
