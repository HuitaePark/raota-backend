package com.raota.domain.ramenShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessHoursDto {
    private String closed_days;
    private String open_time;
    private String close_time;
    private String break_start;
    private String break_end;
}
