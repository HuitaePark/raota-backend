package com.raota.domain.ramenShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ShopStats(
        @Column(name = "visit_count") int visitCount,
        @Column(name = "review_count") int reviewCount
) {
    public static ShopStats init() {
        return new ShopStats(0, 0);
    }

    public ShopStats increaseVisit() {
        return new ShopStats(visitCount + 1, reviewCount);
    }

    public ShopStats increaseReview() {
        return new ShopStats(visitCount, reviewCount + 1);
    }
}