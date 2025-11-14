package com.raota.repository.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.raota.domain.member.controller.response.MyProfileResponse;
import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class MemberQueryRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("유저의 상세 정보를 불러온다.")
    @Test
    void find_user_detail_info(){
        MemberProfile member = MemberProfile.builder()
                .nickname("테스트")
                .imageUrl("http")
                .stats(MemberActivityStats.init())
                .build();

        MemberProfile saved = memberRepository.save(member);

        MyProfileResponse result = memberRepository.findMemberDetailInfo(saved.getId());

        assertThat(result.nickname()).isEqualTo("테스트");
        assertThat(result.profile_image_url()).isEqualTo("http");
        assertThat(result.stats().getTotal_photo_count()).isEqualTo(0);
    }
}
