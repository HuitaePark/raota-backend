package com.raota.domain.member.repository;

import com.raota.domain.member.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
}
