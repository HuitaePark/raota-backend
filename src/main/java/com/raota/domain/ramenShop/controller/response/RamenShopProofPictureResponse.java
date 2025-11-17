package com.raota.domain.ramenShop.controller.response;

import java.time.LocalDateTime;

public record RamenShopProofPictureResponse(Long photo_id,
                                            String image_url,
                                            String uploader_nickname,
                                            LocalDateTime uploaded_at) {
}
