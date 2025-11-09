package com.raota.ramenShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoteResultsDto {
    private Long menu_id;
    private String menu_name;
    private int vote_count;
    private double percentage;
}
