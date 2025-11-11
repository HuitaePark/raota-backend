package com.raota.domain.ramenShop.controller.response;

import com.raota.domain.ramenShop.dto.WaitingSpotDto;
import java.util.List;

public record WaitingSpotResponse(List<WaitingSpotDto> nearby_places) {
}
