package com.raota.ramenShop.controller.response;

import com.raota.ramenShop.dto.WaitingSpotDto;
import java.util.List;

public record WaitingSpotResponse(List<WaitingSpotDto> nearby_places) {
}
