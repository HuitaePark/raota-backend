package com.raota.domain.member.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record MemberActivityStats(
        int visitedRestaurantCount,
        int photoCount,
        int bookmarkCount
) {
    public static MemberActivityStats init(){
        return new MemberActivityStats(0,0,0);
    }

    public MemberActivityStats increaseVisitedRestaurantCount() {
        return new MemberActivityStats(visitedRestaurantCount+1,photoCount,bookmarkCount);
    }
}
