package com.raota.ramenShop.controller.response;

public record ProofPictureInfoResponse(
        Long photo_id,
        String image_url,
        String uploader_nickname,
        String uploaded_at
){
}
