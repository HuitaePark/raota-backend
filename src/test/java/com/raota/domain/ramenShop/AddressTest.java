package com.raota.domain.ramenShop;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("주소의 도로명이 입력되지 않으면 에러가 발생한다.")
    @Test
    void district_in_the_address_is_no_entered(){
        assertThatThrownBy(()->{
            Address address = Address.of("서울시","영등포구","","123");
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("도로명은 필수입니다.");
    }

    @DisplayName("주소의 모든 주소 이름을 출력한다.")
    @Test
    void Print_all_address_names(){
        Address address = Address.of("서울시","영등포구","당산동","123");
        String fullAddress = address.fullAddress();

        assertThat(fullAddress).isEqualTo("서울시 영등포구 당산동 123");
    }

    @DisplayName("주소의 간단한 주소명을 출력한다.")
    @Test
    void Print_simple_address_names(){
        Address address = Address.of("서울시","영등포구","당산동","123");
        String simpleAddress = address.simpleAddress();

        assertThat(simpleAddress).isEqualTo("서울시 영등포구");
    }
}
