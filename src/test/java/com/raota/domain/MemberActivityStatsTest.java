package com.raota.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.raota.domain.member.model.MemberActivityStats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberActivityStatsTest {

    @DisplayName("유저가 식당 방문을 1회 하면 방문 횟수가 증가한다.")
    @Test
    void increase_visited_count() {
        MemberActivityStats stats = MemberActivityStats.init();

        MemberActivityStats plusStats =  stats.increaseVisited();

        assertThat(plusStats.visitedRestaurantCount()).isEqualTo(1);
    }

    @DisplayName("유저가 사진을 업로드 하면 방문 횟수가 증가한다.")
    @Test
    void increase_photo_count() {
        MemberActivityStats stats = MemberActivityStats.init();

        MemberActivityStats plusStats =  stats.increasePhoto();

        assertThat(plusStats.photoCount()).isEqualTo(1);
    }

    @DisplayName("유저가 북마크하면 북마크수가 1 올라간다.")
    @Test
    void increase_bookmark_count() {
        MemberActivityStats stats = MemberActivityStats.init();

        MemberActivityStats plusStats =  stats.increaseBookmark();

        assertThat(plusStats.bookmarkCount()).isEqualTo(1);
    }

    @DisplayName("북마크 수는 감소해도 0 아래로 내려가지 않는다.")
    @Test
    void decrease_bookmark_count_cannot_go_below_zero() {
        MemberActivityStats stats = MemberActivityStats.init(); // bookmarkCount = 0

        MemberActivityStats result = stats.decreaseBookmark();

        assertThat(result.bookmarkCount()).isEqualTo(0); // 음수 방지 확인
    }

}
