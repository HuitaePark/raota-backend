package com.raota.domain.ramenShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventMenuDto {
    private Long id;
    private String name;
    private String description;
    private int price;
    private String image_url;
    private String badge_text;
    private String period;
}
