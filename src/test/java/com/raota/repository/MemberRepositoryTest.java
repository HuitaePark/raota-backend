package com.raota.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.member.controller.response.MyProfileResponse;
import com.raota.domain.member.controller.response.VisitSummaryResponse;
import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.member.repository.MemberRepository;
import com.raota.domain.ramenShop.model.RamenShop;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestDataHelper.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    TestDataHelper testDataHelper;

    private RamenShop ramenShop;

    @BeforeEach
    void setUp(){
        ramenShop = testDataHelper.createRamenShop("테스트 라멘샵");
    }


    @DisplayName("개인정보 수정 후에 바뀐 개인정보를 반환한다.")
    @Test
    void changed_personal_information(){
        //given
        MemberProfile member = MemberProfile.builder()
                .nickname("테스트")
                .imageUrl("http://test.com")
                .stats(MemberActivityStats.init())
                .build();
        memberRepository.save(member);

        //when
        member.updateProfile("업데이트","https://1234");
        entityManager.flush();
        entityManager.clear();//더티체킹 완료
        MemberProfile updated = memberRepository.save(member);

        //then
        assertThat(updated.getNickname()).isEqualTo("업데이트");
        assertThat(updated.getImageUrl()).isEqualTo("https://1234");
    }

    @DisplayName("유저 프로필 닉네임이 비어있으면 에러가 발생한다.")
    @Test
    void userNickname_is_null_error(){
        assertThatThrownBy(()->{
            MemberProfile member = MemberProfile.builder()
                    .nickname(null)
                    .imageUrl("http://test.com")
                    .stats(MemberActivityStats.init())
                    .build();
            memberRepository.save(member);
        });
    }

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

    @DisplayName("유저의 방문한 레스토랑 정보를  불러온다.")
    @Test
    void find_user_visit_restaurant_info(){
        MemberProfile member = testDataHelper.createMember("테스트닝겐");
        PageRequest pageRequest = PageRequest.of(0, 10);

        testDataHelper.createProofPicture(ramenShop, member);
        testDataHelper.createProofPicture(ramenShop, member);

        Page<VisitSummaryResponse> result = memberRepository.findMyVisitRestaurant(member.getId(),pageRequest);
        VisitSummaryResponse first = result.getContent().getFirst();

        assertThat(first.visit_count_for_user()).isEqualTo(2);
        assertThat(first.address_simple()).isEqualTo("서울 마포구");
    }
}
