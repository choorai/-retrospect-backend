package choorai.retrospect.docs;

import static choorai.retrospect.support.ApiDocumentUtils.getDocumentRequest;
import static choorai.retrospect.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import choorai.retrospect.card.entity.Card;
import choorai.retrospect.card.entity.dto.CardRequest;
import choorai.retrospect.card.entity.dto.CardResponse;
import choorai.retrospect.card.service.CardService;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.support.MockUser;
import choorai.retrospect.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@MockUser
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
public class CardDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardService cardService;

    private User currentUser;
    private RetrospectRoom retrospectRoom;

    @BeforeEach
    public void setUp() {
        currentUser = getCurrentUser();
        retrospectRoom = RetrospectRoom.forSave("회고 주제", "회고 상세 내용", "KPT", "01:00:00", "shareLink");
    }

    @DisplayName("Card 생성 테스트")
    @MockUser
    @Test
    void createTest() throws Exception {
        // given
        final CardRequest cardRequest = CardRequest.ofCreate("K", "K회고", 1);
        final CardResponse cardResponse = new CardResponse(1L, "K", "K회고");
        given(cardService.createCard(any(CardRequest.class)))
            .willReturn(cardResponse);

        // when
        final ResultActions result = this.mockMvc.perform(post("/cards/create")
                                                              .header(HttpHeaders.AUTHORIZATION, "유저 토큰")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(cardRequest)));
        // then
        result.andExpect(status().isOk())
            .andDo(document("card-create",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 유저 토큰 정보")
                            ),
                            requestFields(
                                fieldWithPath("type").type(JsonFieldType.STRING).description("타입(K, P, T)"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("retrospectRoomId").type(JsonFieldType.NUMBER).description("회고룸 id")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 Card id"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("생성된 Card의 타입"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("생성된 Card의 내용")
                            )
            ));
    }

    @DisplayName("Card 조회 테스트")
    @MockUser
    @Test
    void readTest() throws Exception {
        // given
        final long cardId = 1L;
        final CardResponse cardResponse = new CardResponse(1L, "K", "K회고");
        given(cardService.getCardResponseById(any(Long.class)))
            .willReturn(cardResponse);
        // when
        final ResultActions result = this.mockMvc.perform(get("/cards/{id}", cardId)
                                                              .header(HttpHeaders.AUTHORIZATION, "유저 토큰")
                                                              .contentType(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk())
            .andDo(document("card-get-by-id",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 유저 토큰 정보")
                            ),
                            pathParameters(
                                parameterWithName("id").description("조회할 카드의 ID")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("조회한 Card id"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("조회한 Card의 타입"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("조회한 Card의 내용")
                            )
            ));
    }

    @DisplayName("Card 전체 조회 테스트")
    @MockUser
    @Test
    void readAllTest() throws Exception {
        // given
        final CardResponse cardResponse1 = new CardResponse(1L, "K", "K회고");
        final CardResponse cardResponse2 = new CardResponse(2L, "K", "K회고2");
        final List<CardResponse> cards = List.of(cardResponse1, cardResponse2);

        when(cardService.getAllCards())
            .thenReturn(cards);
        // when
        ResultActions result = mockMvc.perform(get("/cards/all")
                                                   .header(HttpHeaders.AUTHORIZATION, "유저 토큰")
                                                   .accept(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk())
            .andDo(document("card-get-all",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 유저 토큰 정보")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("카드 목록"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("카드의 ID"),
                                fieldWithPath("data[].type").type(JsonFieldType.STRING).description("카드의 타입"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("카드의 내용")
                            )
            ));
    }

    @Test
    @DisplayName("updateCard 문서화 테스트")
    void updateCardDocsTest() throws Exception {
        // given
        final long cardId = 1L;
        final CardRequest cardRequest = CardRequest.ofUpdate("K", "업데이트 내용");
        final CardResponse cardResponse = new CardResponse(cardId, "K", "업데이트 내용");

        when(cardService.updateCard(any(Long.class), any(CardRequest.class))).thenReturn(cardResponse);

        // when
        ResultActions result = mockMvc.perform(post("/cards/update/{id}", cardId)
                                                   .header(HttpHeaders.AUTHORIZATION, "유저 토큰")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(cardRequest))
                                                   .accept(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk())
            .andDo(document("card-update",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 유저 토큰 정보")
                            ),
                            pathParameters(
                                parameterWithName("id").description("수정할 카드의 ID")
                            ),
                            requestFields(
                                fieldWithPath("type").type(JsonFieldType.STRING).description("수정할 카드의 타입(K, P, T)"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 카드 내용"),
                                fieldWithPath("retrospectRoomId").type(JsonFieldType.NULL).description("(생략)")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("수정된 카드의 ID"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("수정된 카드의 타입"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("수정된 카드의 내용")
                            )
            ));
    }

    @Test
    @DisplayName("deleteCard 문서화 테스트")
    void deleteCardDocsTest() throws Exception {
        // given
        Long cardId = 1L;
        doNothing().when(cardService).deleteCard(cardId);

        // when
        ResultActions result = mockMvc.perform(post("/cards/delete/{id}", cardId)
                                                   .header(HttpHeaders.AUTHORIZATION, "유저 토큰")
                                                   .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
            .andDo(document("card-delete",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 유저 토큰 정보")
                            ),
                            pathParameters(
                                parameterWithName("id").description("삭제할 카드의 ID")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data").optional().description("null 값 전달")
                            )
            ));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}
