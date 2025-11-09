package com.raota.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.ramenShop.controller.RamenProofPictureController;
import com.raota.ramenShop.controller.response.ProofPictureInfoResponse;
import com.raota.ramenShop.service.RamenProofPictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RamenProofPictureControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RamenProofPictureController controller;

    @Mock
    private RamenProofPictureService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("인증샷 업로드에 성공하면 업로드된 사진 정보를 반환한다.")
    @Test
    void view_voting_status() throws Exception {
        Long shopId = 1L;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "702.jpg",
                "image/jpeg",
                "dummy".getBytes()
        );

        ProofPictureInfoResponse response = new  ProofPictureInfoResponse(702L,"https://cdn.menschelin.com/images/user/photo/702.jpg","방금먹음","2025-11-06T10:10:00");
        given(service.addProofPicture(shopId,file)).willReturn(response);

        mockMvc.perform(multipart("/photos/{shopId}", shopId)
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.photo_id").value(702L))
                .andExpect(jsonPath("$.data.image_url").value("https://cdn.menschelin.com/images/user/photo/702.jpg"))
                .andExpect(jsonPath("$.data.uploader_nickname").value("방금먹음"))
                .andExpect(jsonPath("$.data.uploaded_at").value("2025-11-06T10:10:00"));
    }
}