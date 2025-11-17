package com.raota.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.raota.domain.ramenShop.controller.RamenShopInfoController;
import com.raota.domain.ramenShop.controller.request.RamenShopSearchRequest;
import com.raota.domain.ramenShop.controller.response.RamenShopBasicInfoResponse;
import com.raota.domain.ramenShop.controller.response.VisitCountingResponse;
import com.raota.domain.ramenShop.controller.response.VotingStatusResponse;
import com.raota.domain.ramenShop.controller.response.WaitingSpotResponse;
import com.raota.domain.ramenShop.dto.BusinessHoursDto;
import com.raota.domain.ramenShop.dto.EventMenuDto;
import com.raota.domain.ramenShop.dto.NormalMenuDto;
import com.raota.domain.ramenShop.dto.ShopStatDto;
import com.raota.domain.ramenShop.controller.response.StoreSummaryResponse;
import com.raota.domain.ramenShop.dto.VoteResultsDto;
import com.raota.domain.ramenShop.dto.WaitingSpotDto;
import com.raota.domain.ramenShop.service.RamenShopInfoService;
import java.time.LocalTime;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RamenShopInfoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RamenShopInfoController ramenShopInfoController; // Mock 주입 대상

    @Mock
    private RamenShopInfoService ramenShopInfoService; // Mockito Mock

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ramenShopInfoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()) // ← 추가
                .build();
    }

    @DisplayName("방문 인증이 되면 횟수 추가를 하고 결과 정보를 받는다.")
    @Test
    void add_the_count_and_receive_the_results() throws Exception {
        Long shopId = 1L;
        Long userId = 123L;
        VisitCountingResponse visitCountingResponse = new VisitCountingResponse(
                1L,
                123L,
                1251,
                "방문이 인증되었습니다.");
        given(ramenShopInfoService.addVisitCount(shopId, userId)).willReturn(visitCountingResponse);

        mockMvc.perform(post("/ramen-shops/{shopId}/visit", shopId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":123}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.restaurant_id").value(1))
                .andExpect(jsonPath("$.data.user_id").value(123))
                .andExpect(jsonPath("$.data.new_visit_count").value(1251))
                .andExpect(jsonPath("$.data.message").value("방문이 인증되었습니다."));
    }

    @DisplayName("투표 후 바뀐 투표 현황을 확인한다.")
    @Test
    void view_voting_status_after_vote() throws Exception {
        Long shopId = 1L;
        Long menuId = 1L;
        VotingStatusResponse response = new VotingStatusResponse(620,
                List.of(new VoteResultsDto(1L, "시오라멘", 410L, 66.1)));
        given(ramenShopInfoService.voteTheMenu(shopId, menuId)).willReturn(response);

        mockMvc.perform(post("/ramen-shops/{shopId}/votes/{menuId}", shopId, menuId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.total_votes").value(620))
                // 배열 값 확인
                .andExpect(jsonPath("$.data.vote_results[0].menu_id").value(1))
                .andExpect(jsonPath("$.data.vote_results[0].menu_name").value("시오라멘"))
                .andExpect(jsonPath("$.data.vote_results[0].vote_count").value(410))
                .andExpect(jsonPath("$.data.vote_results[0].percentage").value(66.1));
    }

    @DisplayName("주변 대기장소 목록을 조회한다.")
    @Test
    void get_nearBy_waiting_spot() throws Exception {
        Long shopId = 1L;
        WaitingSpotResponse response = new WaitingSpotResponse(List.of(new WaitingSpotDto(
                "cafe_123",
                "스타벅스 홍대역점",
                "카페",
                410,
                "서울 마포구 양화로 123",
                "https://cdn.menschelin.com/images/places/starbucks_hongdae.jpg")));
        given(ramenShopInfoService.getWaitingSpot(shopId)).willReturn(response);

        mockMvc.perform(get("/ramen-shops/{shopId}/nearby", shopId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                // 배열 값 확인
                .andExpect(jsonPath("$.data.nearby_places[0].place_id").value("cafe_123"))
                .andExpect(jsonPath("$.data.nearby_places[0].name").value("스타벅스 홍대역점"))
                .andExpect(jsonPath("$.data.nearby_places[0].category").value("카페"))
                .andExpect(jsonPath("$.data.nearby_places[0].distance_meters").value(410))
                .andExpect(jsonPath("$.data.nearby_places[0].address").value("서울 마포구 양화로 123"))
                .andExpect(jsonPath("$.data.nearby_places[0].image_url").value(
                        "https://cdn.menschelin.com/images/places/starbucks_hongdae.jpg"));
    }

    @DisplayName("라멘집 기본 상세정보를 반환한다.")
    @Test
    void get_shop_detail() throws Exception{
        Long shopId = 101L;
        RamenShopBasicInfoResponse response = buildResponse();
        given(ramenShopInfoService.getShopDetailInfo(shopId)).willReturn(response);

        mockMvc.perform(get("/ramen-shops/{shopId}", shopId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))

                // 최상단 필드
                .andExpect(jsonPath("$.data.id").value(101))
                .andExpect(jsonPath("$.data.name").value("켄비멘리키"))
                .andExpect(jsonPath("$.data.image_url").value("https://cdn.menschelin.com/images/rest/101/main.jpg"))
                .andExpect(jsonPath("$.data.address").value("서울 마포구 서교동 396-12"))
                .andExpect(jsonPath("$.data.instagram_url").value("https://instagram.com/kenbimen_riki"))

                // stats
                .andExpect(jsonPath("$.data.stats.visit_count").value(1250))
                .andExpect(jsonPath("$.data.stats.bookmark_count").value(342))

                // tags
                .andExpect(jsonPath("$.data.tags[0]").value("#시오라멘"))
                .andExpect(jsonPath("$.data.tags[1]").value("#츠케멘"))

                // normal_menus
                .andExpect(jsonPath("$.data.normal_menus[0].id").value(1))
                .andExpect(jsonPath("$.data.normal_menus[0].name").value("시오라멘"))
                .andExpect(jsonPath("$.data.normal_menus[0].price").value(9000))
                .andExpect(jsonPath("$.data.normal_menus[0].signature").value(true))
                .andExpect(jsonPath("$.data.normal_menus[0].image_url")
                        .value("https://cdn.menschelin.com/images/event/pink_choco.jpg"))

                // event_menus
                .andExpect(jsonPath("$.data.event_menus[0].id").value(501))
                .andExpect(jsonPath("$.data.event_menus[0].name").value("핑크 초코 라멘"))
                .andExpect(jsonPath("$.data.event_menus[0].price").value(12000))
                .andExpect(jsonPath("$.data.event_menus[0].badge_text").value("발렌타인 한정"));
    }

    @DisplayName("라멘 가게 리스트를 조회한다.")
    @Test
    void get_home_info() throws Exception {

        var featuredStores = List.of(
                new StoreSummaryResponse(1L, "라멘스키 강남점", "서울 강남구", List.of("돈코츠라멘"),
                        "https://cdn.mensulang.kr/stores/1.jpg"),
                new StoreSummaryResponse(2L, "라멘스키 홍대점", "서울 마포구", List.of("돈코츠라멘"),
                        "https://cdn.mensulang.kr/stores/2.jpg")
        );

        PageRequest pr = PageRequest.of(0, 2);
        Page<StoreSummaryResponse> page = new PageImpl<>(featuredStores, pr, 5);
        // -----------------------------------------------------------

        // 서비스 모킹
        given(ramenShopInfoService.getRamenShopList(any(RamenShopSearchRequest.class), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/ramen-shops")
                        .param("page", "0")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))

                // content 배열 검증
                .andExpect(jsonPath("$.data.content[0].id").value(1))
                .andExpect(jsonPath("$.data.content[0].name").value("라멘스키 강남점"))
                .andExpect(jsonPath("$.data.content[0].address").value("서울 강남구"))
                .andExpect(jsonPath("$.data.content[0].imageUrl")
                        .value("https://cdn.mensulang.kr/stores/1.jpg"))

                .andExpect(jsonPath("$.data.content[1].id").value(2))
                .andExpect(jsonPath("$.data.content[1].name").value("라멘스키 홍대점"))
                .andExpect(jsonPath("$.data.content[1].address").value("서울 마포구"))
                .andExpect(jsonPath("$.data.content[1].imageUrl")
                        .value("https://cdn.mensulang.kr/stores/2.jpg"))

                // 페이지 메타데이터 검증
                .andExpect(jsonPath("$.data.totalElements").value(5))
                .andExpect(jsonPath("$.data.size").value(2))
                .andExpect(jsonPath("$.data.number").value(0))          // 현재 페이지 index
                .andExpect(jsonPath("$.data.totalPages").value(3))      // ceil(5/2)=3
                .andExpect(jsonPath("$.data.first").value(true))
                .andExpect(jsonPath("$.data.last").value(false))
                .andExpect(jsonPath("$.data.numberOfElements").value(2))
                .andExpect(jsonPath("$.data.empty").value(false));
    }




    private RamenShopBasicInfoResponse buildResponse(){
        BusinessHoursDto hours = new BusinessHoursDto(
                /* closedDays */ "없음",
                /* openTime   */ LocalTime.now(),
                /* closeTime  */ LocalTime.now(),
                /* breakStart */ LocalTime.now(),
                /* breakEnd   */ LocalTime.now()
        );

        ShopStatDto stats = new ShopStatDto(1250, 342);

        List<String> tags = List.of("#시오라멘", "#츠케멘");

        List<NormalMenuDto> normalMenus = List.of(
                new NormalMenuDto(1L, "시오라멘", 9000, true,  "https://cdn.menschelin.com/images/event/pink_choco.jpg"),
                new NormalMenuDto(2L, "니보시 츠케멘", 10000, true, "https://cdn.menschelin.com/images/event/pink_choco.jpg"),
                new NormalMenuDto(3L, "카라구치 라멘", 9500, false, "https://cdn.menschelin.com/images/event/pink_choco.jpg"),
                new NormalMenuDto(4L, "차슈동", 4000, false, "https://cdn.menschelin.com/images/event/pink_choco.jpg")
        );

        List<EventMenuDto> eventMenus = List.of(
                new EventMenuDto(
                        501L,
                        "핑크 초코 라멘",
                        "달콤한 화이트 초콜릿과 돈코츠 육수의 의외의 조합! 핑크빛 면이 사랑스러운 한정판 라멘.",
                        12000,
                        "https://cdn.menschelin.com/images/event/pink_choco.jpg",
                        "발렌타인 한정"
                )
        );

        return RamenShopBasicInfoResponse.builder()
                .id(101L)
                .name("켄비멘리키")
                .image_url("https://cdn.menschelin.com/images/rest/101/main.jpg")
                .address("서울 마포구 서교동 396-12")
                .instagram_url("https://instagram.com/kenbimen_riki")
                .business_hours(hours)
                .stats(stats)
                .tags(tags)
                .normal_menus(normalMenus)
                .event_menus(eventMenus)
                .build();
    }
}
