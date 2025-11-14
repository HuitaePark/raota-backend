package com.raota;

import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.member.repository.MemberRepository;
import com.raota.domain.proofPicture.model.RamenProofPicture;
import com.raota.domain.proofPicture.repository.RamenProofPictureRepository;
import com.raota.domain.ramenShop.model.RamenShop;
import com.raota.domain.ramenShop.repository.RamenShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataHelper {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RamenProofPictureRepository proofPictureRepository;
    @Autowired
    private RamenShopRepository ramenShopRepository;

    public MemberProfile createMember(String nickname) {
        MemberProfile member = MemberProfile.builder()
                .nickname(nickname)
                .imageUrl("http://test.com/profile.png")
                .stats(MemberActivityStats.init())
                .build();
        return memberRepository.save(member);
    }

    public RamenShop createRamenShop(String name) {
        RamenShop shop = RamenShop.builder()
                .name(name)
                .build();
        return ramenShopRepository.save(shop);
    }

    public RamenProofPicture createProofPicture(RamenShop shop, MemberProfile member) {
        RamenProofPicture picture = RamenProofPicture.builder()
                .ramenShop(shop)
                .memberProfile(member)
                .imageName("test-image.png")
                .imageUrl("http://test.com/image.png")
                .description("test description")
                .build();

        return proofPictureRepository.save(picture);
    }

}
