package com.raota.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.raota.domain.ramenShop.model.BusinessHours;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BusinessHoursTest{

    @DisplayName("영업 종료시간이 시작시간 이전이면 에러가 발생한다.")
    @Test
    void closing_time_is_before_the_opening_time(){
        assertThatThrownBy(()->{
            BusinessHours hours = BusinessHours.of(
                "없음",
                LocalTime.of(21, 30),
                LocalTime.of(11, 0),
                LocalTime.of(15, 0),
                LocalTime.of(17, 30));
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("영업 종료 시간은 시작 시간 이후여야 합니다.");
    }
}
