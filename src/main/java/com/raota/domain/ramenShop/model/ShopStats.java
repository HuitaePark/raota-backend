package com.raota.domain.ramenShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ShopStats(
        @Column(name = "visit_count") int visitCount,
        @Column(name = "bookmark_count") int bookmarkCount
) {
    public static ShopStats init() {
        return new ShopStats(0, 0);
    }

    public ShopStats increaseVisit() {
        return new ShopStats(visitCount + 1, bookmarkCount);
    }

    public ShopStats increaseBookmark() {
        return new ShopStats(visitCount, bookmarkCount + 1);
    }

    public ShopStats decreaseVisit() {
        if (visitCount <= 0) {
            return new ShopStats(0, bookmarkCount);
        }
        return new ShopStats(visitCount - 1, bookmarkCount);
    }
}