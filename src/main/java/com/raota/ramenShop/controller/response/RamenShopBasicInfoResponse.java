package com.raota.ramenShop.controller.response;

import com.raota.ramenShop.dto.BusinessHoursDto;
import com.raota.ramenShop.dto.EventMenuDto;
import com.raota.ramenShop.dto.NormalMenuDto;
import com.raota.ramenShop.dto.StatDto;
import java.util.List;
import lombok.Builder;

@Builder
public record RamenShopBasicInfoResponse(
        Long id,
        String name,
        String image_url,
        String address,
        String instagram_url,
        BusinessHoursDto business_hours,
        StatDto stats,
        List<String> tags,
        List<NormalMenuDto> normal_menus,
        List<EventMenuDto> event_menus) {
}
