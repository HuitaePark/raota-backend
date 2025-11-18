package com.raota.unit.domain.ramenShop;

import static org.assertj.core.api.Assertions.assertThat;

import com.raota.domain.ramenShop.model.ShopStats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShopStatsTest {

    @DisplayName("방문을 1회하면 방문수가 1회 증가한다.")
    @Test
    void increase_visit_count_test(){
        ShopStats shopStats = ShopStats.init();
        assertThat(shopStats.visitCount()).isEqualTo(0);

        ShopStats plusStats = shopStats.increaseVisit();

        assertThat(plusStats.visitCount()).isEqualTo(1);
    }

    @DisplayName("북마크를 하면 북마크 수가 1회 증가한다.")
    @Test
    void increase_review_count_test(){
        ShopStats shopStats = ShopStats.init();
        assertThat(shopStats.bookmarkCount()).isEqualTo(0);

        ShopStats plusStats = shopStats.increaseBookmark();

        assertThat(plusStats.bookmarkCount()).isEqualTo(1);
    }

    @DisplayName("방문 기록을 삭제하면 방문수가 1 감소한다. 또한 0 아래로 감소하지 않는다.")
    @Test
    void decrease_visit_count_test(){
        ShopStats shopStats = ShopStats.init();
        assertThat(shopStats.visitCount()).isEqualTo(0);

        ShopStats plusStats = shopStats.increaseVisit();
        ShopStats minusStats = plusStats.decreaseVisit();
        assertThat(minusStats.visitCount()).isEqualTo(0);

        ShopStats minusStats2 = minusStats.decreaseVisit();
        assertThat(minusStats2.visitCount()).isEqualTo(0);
    }

    @DisplayName("북마크를 삭제하면 북마크 수가 1 감소한다. 또한 0 아래로 감소하지 않는다.")
    @Test
    void decrease_bookmark_count_test(){
        ShopStats shopStats = ShopStats.init();
        assertThat(shopStats.bookmarkCount()).isEqualTo(0);

        ShopStats plusStats = shopStats.increaseBookmark();
        ShopStats minusStats = plusStats.decreaseBookmark();
        assertThat(minusStats.bookmarkCount()).isEqualTo(0);

        ShopStats minusStats2 = minusStats.decreaseBookmark();
        assertThat(minusStats2.bookmarkCount()).isEqualTo(0);
    }
}
