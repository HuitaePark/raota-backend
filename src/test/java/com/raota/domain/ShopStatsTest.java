package com.raota.domain;

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
}
