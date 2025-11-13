package com.raota.domain.member.model;

import jakarta.persistence.Embedded;
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
    public MemberProfile(Long id, String imageUrl, String nickname,MemberActivityStats stats) {
        verifyNicknameBlank(nickname);
        this.id = id;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.memberActivityStats = stats;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String imageUrl;

    @Embedded
    private MemberActivityStats memberActivityStats;

    private void verifyNicknameBlank(String nickname) {
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 공백일수 없습니다.");
        }
    }

}
