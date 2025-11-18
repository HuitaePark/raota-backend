package com.raota.repository;

import com.raota.domain.member.model.Bookmark;
import com.raota.domain.member.model.MemberActivityStats;
import com.raota.domain.member.model.MemberProfile;
import com.raota.domain.member.repository.BookmarkRepository;
import com.raota.domain.member.repository.MemberRepository;
import com.raota.domain.proofPicture.model.RamenProofPicture;
import com.raota.domain.proofPicture.repository.RamenProofPictureRepository;
import com.raota.domain.ramenShop.model.Address;
import com.raota.domain.ramenShop.model.RamenShop;
import com.raota.domain.ramenShop.repository.RamenShopRepository;
import java.util.List;
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
    @Autowired
    private BookmarkRepository bookmarkRepository;

    public MemberProfile createMember(String nickname) {
        MemberProfile member = MemberProfile.builder()
                .nickname(nickname)
                .imageUrl("http://test.com/profile.png")
                .stats(MemberActivityStats.init())
                .build();
        return memberRepository.save(member);
    }

    public RamenShop createRamenShop(String name,Address address) {
        RamenShop shop = RamenShop.builder()
                .name(name)
                .address(address)
                .tags(List.of("돈코츠라멘","아부라소바"))
                .build();
        return ramenShopRepository.save(shop);
    }

    public RamenProofPicture createProofPicture(RamenShop shop, MemberProfile member) {
        RamenProofPicture picture = RamenProofPicture.builder()
                .ramenShop(shop)
                .memberProfile(member)
                .imageName("test-image.png")
                .imageUrl("http://test.com/image.png")
                .build();

        return proofPictureRepository.save(picture);
    }

    public Bookmark createBookmark(RamenShop ramenShop, MemberProfile member) {
        Bookmark bookmark = new Bookmark(
                null,
                member,
                ramenShop,
                null // markingAt → @CreationTimestamp 가 자동 생성
        );

        return bookmarkRepository.save(bookmark);
    }

}
