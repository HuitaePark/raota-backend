package com.raota.domain.member.repository;

import com.raota.domain.member.model.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberProfile,Long> {
}
