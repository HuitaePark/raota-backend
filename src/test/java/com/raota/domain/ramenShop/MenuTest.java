package com.raota.domain.ramenShop;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.ramenShop.model.EventMenu;
import com.raota.domain.ramenShop.model.EventMenus;
import com.raota.domain.ramenShop.model.NormalMenu;
import com.raota.domain.ramenShop.model.NormalMenus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuTest {


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

        NormalMenus normalMenus = NormalMenus.init();

        normalMenus.add(menu);
        assertThatThrownBy(() -> normalMenus.add(menu2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 메뉴 이름입니다: 시오라멘");

    }
    @DisplayName("중복된 이벤트 메뉴이름이 있다면 에러가 발생한다.")
    @Test
    void duplicate_event_menu_names() {

        EventMenu menu = EventMenu.builder()
                .name("핑크 초코 라멘")
                .price(12000)
                .badgeText("발렌타인 한정")
                .imageUrl("image1.jpg")
                .description("특별한 맛!")
                .build();

        EventMenu menu2 = EventMenu.builder()
                .name("핑크 초코 라멘")  // 동일 이름
                .price(12000)
                .badgeText("발렌타인 한정")
                .imageUrl("image2.jpg")
                .description("특별한 맛2!")
                .build();

        EventMenus eventMenus = EventMenus.init();

        eventMenus.add(menu);
        assertThatThrownBy(() -> eventMenus.add(menu2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 메뉴 이름입니다: 핑크 초코 라멘");
    }
}
