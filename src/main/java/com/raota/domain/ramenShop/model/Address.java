package com.raota.domain.ramenShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Address(
        @Column(name = "city") String city,
        @Column(name = "district") String district,
        @Column(name = "street") String street,
        @Column(name = "detail") String detail
) {
    public Address{
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("도시는 필수입니다.");
        }
    }

    public static Address of(String city, String district, String street, String detail) {
        return new Address(city, district, street, detail);
    }
}
