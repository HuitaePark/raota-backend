package com.raota.domain.ramenShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WaitingSpotDto {
    private String place_id;
    private String name;
    private String category;
    private int distance_meters;
    private String address;
    private String image_url;
}
