package com.raota.domain.ramenShop;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.ramenShop.model.NormalMenu;
import com.raota.domain.ramenShop.model.NormalMenus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NormalMenuTest {

    @DisplayName("중복된 메뉴이름이 있다면 에러가 발생한다.")
    @Test
    void duplicate_menu_names() {
        NormalMenu menu = NormalMenu.builder()
                .name("시오라멘")
                .price(9000)
                .isSignature(true)
                .imageUrl("https://cdn.men.com/salt.jpg")
                .build();

        NormalMenu menu2 = NormalMenu.builder()
                .name("시오라멘")
                .price(9000)
                .isSignature(true)
                .imageUrl("https://cdn.men.com/salt.jpg")
                .build();

        NormalMenus normalMenus = new NormalMenus();
        normalMenus.add(menu);
        assertThatThrownBy(() -> normalMenus.add(menu2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 메뉴 이름입니다: 시오라멘");

    }
}
