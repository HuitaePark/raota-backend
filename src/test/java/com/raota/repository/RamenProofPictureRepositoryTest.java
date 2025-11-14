package com.raota.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.proofPicture.model.RamenProofPicture;
import com.raota.domain.proofPicture.repository.RamenProofPictureRepository;
import com.raota.domain.ramenShop.model.RamenShop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class RamenProofPictureRepositoryTest {

    @Autowired
    private RamenProofPictureRepository ramenProofPictureRepository;

    MemberProfile member;
    RamenShop ramenShop;

    @BeforeEach
    void setUp(){
        member = MemberProfile.builder()
                .nickname("테스트")
                .imageUrl("http://test.com")
                .stats(MemberActivityStats.init())
                .build();

        ramenShop = RamenShop.builder()
                .name("라멘집")
                .build();
    }

    @DisplayName("이미지 url이 비어있으면 에러가 발생한다.")
    @Test
    void if_imageUrl_is_empty(){
        assertThatThrownBy(()->{
            RamenProofPicture picture = RamenProofPicture.builder()
                    .memberProfile(member)
                    .ramenShop(ramenShop)
                    .build();
            ramenProofPictureRepository.save(picture);
        });
    }

}
