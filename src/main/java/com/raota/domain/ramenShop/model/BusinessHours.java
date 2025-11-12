package com.raota.domain.ramenShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;


@Embeddable
public record BusinessHours(
        @Column(name = "closed_days", length = 50)
        String closedDays,

        @Column(name = "open_time")
        LocalTime openTime,

        @Column(name = "close_time")
        LocalTime closeTime,

        @Column(name = "break_start")
        LocalTime breakStart,

        @Column(name = "break_end")
        LocalTime breakEnd
) {
    public BusinessHours {
        if (openTime != null && closeTime != null && !closeTime.isAfter(openTime)) {
            throw new IllegalArgumentException("영업 종료 시간은 시작 시간 이후여야 합니다.");
        }
    }

    public static BusinessHours of(String closedDays, LocalTime open, LocalTime close, LocalTime breakStart, LocalTime breakEnd) {
        return new BusinessHours(closedDays, open, close, breakStart, breakEnd);
    }

    public String toDisplayString() {
        return "%s %s~%s (Break %s~%s)".formatted(
                closedDays != null ? closedDays : "없음",
                openTime, closeTime, breakStart, breakEnd
        );
    }
}