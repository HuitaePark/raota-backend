package com.raota.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager entityManager;

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

}
