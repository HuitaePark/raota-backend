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

    public MemberActivityStats increaseVisited(){
        return new MemberActivityStats(visitedRestaurantCount+1,photoCount,bookmarkCount);
    }

    public MemberActivityStats increasePhoto() {
        return new MemberActivityStats(this.visitedRestaurantCount, this.photoCount + 1, this.bookmarkCount);
    }
}
