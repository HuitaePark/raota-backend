package com.raota.domain.ramenShop.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "normal_menu")
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "normal_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ramen_shop_id",nullable = false)
    private RamenShop ramenShop;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer price;

    @Column(name = "is_signature", nullable = false)
    private Boolean isSignature = false;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}