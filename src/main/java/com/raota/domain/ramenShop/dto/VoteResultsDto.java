package com.raota.domain.ramenShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoteResultsDto {
    private Long menu_id;
    private String menu_name;
    private Long vote_count;
    private Double percentage;
}
