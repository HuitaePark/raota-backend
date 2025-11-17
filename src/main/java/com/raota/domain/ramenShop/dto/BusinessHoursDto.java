package com.raota.domain.ramenShop.dto;

import com.raota.domain.ramenShop.model.BusinessHours;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessHoursDto {
    private String closed_days;
    private LocalTime open_time;
    private LocalTime close_time;
    private LocalTime break_start;
    private LocalTime break_end;

    public static BusinessHoursDto from(BusinessHours businessHours){
        return new BusinessHoursDto(
                businessHours.closedDays(),
                businessHours.openTime(),
                businessHours.closeTime(),
                businessHours.breakStart(),
                businessHours.breakEnd()
        );
    }
}
