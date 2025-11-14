package com.raota.domain.proofPicture.controller.response;

import java.time.LocalDateTime;

public record ProofPictureInfoResponse(
        Long photo_id,
        String image_url,
        String uploader_nickname,
        LocalDateTime uploaded_at
){
}
