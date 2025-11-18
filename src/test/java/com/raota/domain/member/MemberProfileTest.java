package com.raota.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberProfileTest {

    @DisplayName("닉네임은 비어있을수 없다.")
    @Test
    void Nicknames_cant_be_empty() {
        assertThatThrownBy(() -> {
            MemberProfile memberProfile = MemberProfile.builder()
                    .nickname("")
                    .imageUrl("http://1234.com")
                    .build();
        });
    }

    @DisplayName("닉네임을 수정할때 공백이거나 null이면 에러를 반환한다.")
    @Test
    void nickname_is_null() {
        MemberProfile memberProfile = MemberProfile.builder()
                .id(1L)
                .nickname("안녕")
                .stats(MemberActivityStats.init())
                .build();

        assertThatThrownBy(() -> memberProfile.updateProfile(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("닉네임은 null 또는 빈 값일 수 없습니다.");
    }
}
