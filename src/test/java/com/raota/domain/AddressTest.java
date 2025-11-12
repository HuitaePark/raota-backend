package com.raota.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.ramenShop.model.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddressTest {

    @DisplayName("주소의 도시가 입력되지 않으면 에러가 발생한다.")
    @Test
    void city_in_the_address_is_no_entered(){
        assertThatThrownBy(()->{
            Address address = Address.of("","영등포구","당산동","123");
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("도시는 필수입니다.");
    }
}
