package com.raota.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.member.model.MemberProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberProfileTest {

    @DisplayName("닉네임은 비어있을수 없다.")
    @Test
    void Nicknames_cant_be_empty(){
        assertThatThrownBy(()->{
            MemberProfile memberProfile = MemberProfile.builder()
                    .nickname("")
                    .imageUrl("http://1234.com")
                    .build();
        });
    }
}
