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

    public MemberActivityStats increaseBookmark() {
        return new MemberActivityStats(this.visitedRestaurantCount, this.photoCount, this.bookmarkCount + 1);
    }

    public MemberActivityStats decreaseVisit() {
        return new MemberActivityStats(
                Math.max(0, visitedRestaurantCount - 1),
                photoCount,
                bookmarkCount
        );
    }

    public MemberActivityStats decreasePhoto() {
        return new MemberActivityStats(
                visitedRestaurantCount,
                Math.max(0, photoCount - 1),
                bookmarkCount
        );
    }

    public MemberActivityStats decreaseBookmark() {
        return new MemberActivityStats(
                visitedRestaurantCount,
                photoCount,
                Math.max(0, bookmarkCount - 1)
        );
    }
}


