package com.raota.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.domain.ramenShop.controller.RamenProofPictureController;
import com.raota.domain.ramenShop.controller.response.ProofPictureInfoResponse;
import com.raota.domain.ramenShop.service.RamenProofPictureService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RamenProofPictureControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RamenProofPictureController controller;

    @Mock
    private RamenProofPictureService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
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

    @Test
    @DisplayName("인증샷 목록을 페이지로 조회하면 Page 메타와 콘텐츠를 반환한다")
    void get_proof_pictures_returns_page() throws Exception {
        long shopId = 1L;

        // 콘텐츠 더미
        ProofPictureInfoResponse r1 = new ProofPictureInfoResponse(
                702L,
                "https://cdn.menschelin.com/images/user/photo/702.jpg",
                "방금먹음",
                "2025-11-06T10:10:00"
        );
        ProofPictureInfoResponse r2 = new ProofPictureInfoResponse(
                703L,
                "https://cdn.menschelin.com/images/user/photo/703.jpg",
                "또먹음",
                "2025-11-07T09:00:00"
        );

        PageRequest pr = PageRequest.of(0, 2);
        Page<ProofPictureInfoResponse> page = new PageImpl<>(List.of(r1, r2), pr, 5);

        given(service.findProofPicture(eq(shopId), eq(pr))).willReturn(page);

        mockMvc.perform(get("/photos/{shopId}", shopId)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                // 콘텐츠 검증
                .andExpect(jsonPath("$.data.content[0].photo_id").value(702L))
                .andExpect(jsonPath("$.data.content[0].image_url").value("https://cdn.menschelin.com/images/user/photo/702.jpg"))
                .andExpect(jsonPath("$.data.content[1].photo_id").value(703L))
                // 페이지 메타 검증(주요 필드만)
                .andExpect(jsonPath("$.data.number").value(0))           // 현재 페이지
                .andExpect(jsonPath("$.data.size").value(2))             // 요청 사이즈
                .andExpect(jsonPath("$.data.totalElements").value(5))    // 총 개수
                .andExpect(jsonPath("$.data.totalPages").value(3))       // 총 페이지
                .andExpect(jsonPath("$.data.first").value(true))
                .andExpect(jsonPath("$.data.last").value(false))
                .andExpect(jsonPath("$.data.numberOfElements").value(2));

        then(service).should().findProofPicture(eq(shopId), eq(pr));
    }
}