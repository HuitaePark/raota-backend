package com.raota.domain.ramenShop.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RamenShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ramen_shop_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private BusinessHours businessHours;

    @Embedded
    @Builder.Default
    private ShopStats stats = ShopStats.init();

    @Type(JsonType.class)
    @Column(name = "tags",columnDefinition = "json")
    private List<String> tags;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Embedded
    private NormalMenus normalMenus;

    @Embedded
    private EventMenus eventMenus;

}
