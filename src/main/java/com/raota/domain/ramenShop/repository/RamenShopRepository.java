package com.raota.domain.ramenShop.repository;

import com.raota.domain.ramenShop.controller.response.RamenShopBasicInfoResponse;
import com.raota.domain.ramenShop.model.RamenShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RamenShopRepository extends JpaRepository<RamenShop,Long> {

    RamenShopBasicInfoResponse findBasicInfoById(Long id);
}
