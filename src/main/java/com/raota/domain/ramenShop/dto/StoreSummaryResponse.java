package com.raota.domain.ramenShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreSummaryResponse {
    private Long id;
    private String name;
    private String address;
    private String category;
    private String imageUrl;
}
