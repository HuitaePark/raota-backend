package com.raota.domain.ramenShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NormalMenuDto {
    private Long id;
    private String name;
    private int price;
    private boolean signature;
    private String image_url;
}