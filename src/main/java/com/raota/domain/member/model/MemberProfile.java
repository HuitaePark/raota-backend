package com.raota.domain.member.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class MemberProfile {

    @Builder
    public MemberProfile(int bookmarkCount, Long id, String imageUrl, String nickname, int photoCount,
                         int visitedRestaurantCount) {
        verifyNicknameBlank(nickname);
        this.bookmarkCount = bookmarkCount;
        this.id = id;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.photoCount = photoCount;
        this.visitedRestaurantCount = visitedRestaurantCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String imageUrl;

    private int visitedRestaurantCount = 0;
    private int photoCount = 0;
    private int bookmarkCount = 0;

    private void verifyNicknameBlank(String nickname) {
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 공백일수 없습니다.");
        }
    }
}
