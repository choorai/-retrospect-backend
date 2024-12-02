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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import choorai.retrospect.retrospect_room.card.service.CardService;
import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.card.service.dto.CardUpdateRequest;
import choorai.retrospect.support.MockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
public class CardDocumentTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardService cardService;

    @DisplayName("Card 조회 api 문서 테스트")
    @Test
    void readTest() throws Exception {
        // given
        final Long retrospectRoomId = 1L;
        final Long cardId = 1L;
        final CardResponse cardResponse = new CardResponse(1L, "KEEP", "KEEP회고");
        given(cardService.getCardResponseById(any(Long.class), any(Long.class)))
            .willReturn(cardResponse);
        // when
        final ResultActions result = this.mockMvc.perform(
            get("/retrospect-room/{retrospectRoomId}/cards/{cardId}", retrospectRoomId, cardId)
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
                                parameterWithName("retrospectRoomId").description("조회할 카드가 속한 retrospect room의 id"),
                                parameterWithName("cardId").description("조회할 카드의 ID")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("조회한 Card id"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("조회한 Card의 타입"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("조회한 Card의 내용")
                            )
            ));
    }

    @DisplayName("Card 전체 조회 api 문서 테스트")
    @Test
    void readAllTest() throws Exception {
        // given
        final Long retrospectRoomId = 1L;
        final CardResponse cardResponse1 = new CardResponse(1L, "KEEP", "K회고");
        final CardResponse cardResponse2 = new CardResponse(2L, "KEEP", "K회고2");
        final List<CardResponse> cards = List.of(cardResponse1, cardResponse2);

        when(cardService.getAllCards(any(Long.class)))
            .thenReturn(cards);
        // when
        ResultActions result = mockMvc.perform(get("/retrospect-room/{retrospectRoomId}/cards", retrospectRoomId)
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
                            pathParameters(
                                parameterWithName("retrospectRoomId").description("조회할 카드가 속한 retrospect room의 id")
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

    @DisplayName("Card 생성 api 문서 테스트")
    @MockUser
    @Test
    void createTest() throws Exception {
        // given
        final Long retrospectRoomId = 1L;
        final CardCreateRequest cardRequest = new CardCreateRequest("KEEP", "KEEP회고");
        final CardResponse cardResponse = new CardResponse(1L, "KEEP", "KEEP회고");
        given(cardService.createCard(any(Long.class), any(CardCreateRequest.class)))
            .willReturn(cardResponse);
        // when
        final ResultActions result = this.mockMvc.perform(
            RestDocumentationRequestBuilders.post("/retrospect-room/{retrospectRoomId}/createCard", retrospectRoomId)
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
                            pathParameters(
                                parameterWithName("retrospectRoomId").description("만들어질 카드가 속한 retrospectRoom의 id")
                            ),
                            requestFields(
                                fieldWithPath("type").type(JsonFieldType.STRING).description("타입(KEEP, PROBLEM, TRY)"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 Card id"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("생성된 Card의 타입"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("생성된 Card의 내용")
                            )
            ));
    }

    @Test
    @DisplayName("Card update api 문서 테스트")
    void updateCardDocsTest() throws Exception {
        // given
        final Long cardId = 1L;
        final Long retrospectRoomId = 1L;
        final CardUpdateRequest cardRequest = new CardUpdateRequest("KEEP", "업데이트 내용");
        final CardResponse cardResponse = new CardResponse(cardId, "KEEP", "업데이트 내용");

        when(cardService.updateCard(any(Long.class), any(Long.class), any(CardUpdateRequest.class)))
            .thenReturn(cardResponse);

        // when
        ResultActions result = mockMvc.perform(
            patch("/retrospect-room/{retrospectRoomId}/cards/{cardId}", retrospectRoomId, cardId)
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
                                parameterWithName("retrospectRoomId").description("수정할 카드가 속한 retrospect room의 id"),
                                parameterWithName("cardId").description("수정할 카드의 ID")
                            ),
                            requestFields(
                                fieldWithPath("type").type(JsonFieldType.STRING)
                                    .description("수정할 카드의 타입(KEEP, PROBLEM, TRY)"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 카드 내용")
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
    @DisplayName("deleteCard api 문서 테스트")
    void deleteCardDocsTest() throws Exception {
        // given
        Long cardId = 1L;
        Long retrospectRoomId = 1L;
        doNothing().when(cardService).deleteCard(any(Long.class), any(Long.class));

        // when
        ResultActions result = mockMvc.perform(
            delete("/retrospect-room/{retrospectRoomId}/cards/{cardId}", retrospectRoomId, cardId)
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
                                parameterWithName("retrospectRoomId").description("삭제할 카드가 속한 retrospect room의 id"),
                                parameterWithName("cardId").description("삭제할 카드의 ID")
                            ),
                            responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                                fieldWithPath("data").optional().description("null 값 전달")
                            )
            ));
    }
}
