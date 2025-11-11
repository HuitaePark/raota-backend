package com.raota.domain.ramenShop.controller.response;

import com.raota.domain.ramenShop.dto.StoreSummaryResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record RamenShopListResponse(
        List<StoreSummaryResponse> featuredStores) {
}
