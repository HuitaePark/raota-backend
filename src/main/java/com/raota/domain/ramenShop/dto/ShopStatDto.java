package com.raota.domain.ramenShop.dto;

import com.raota.domain.ramenShop.model.ShopStats;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShopStatDto {
    private int visit_count;
    private int bookmark_count;

    public static ShopStatDto from(ShopStats stats) {
        return new ShopStatDto(
                stats.visitCount(),
                stats.bookmarkCount()
        );
    }
}
