package choorai.retrospect.docs;

import choorai.retrospect.retrospect_room.service.RetrospectRoomService;
import choorai.retrospect.retrospect_room.service.dto.CreateRequest;
import choorai.retrospect.retrospect_room.service.dto.CreateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static choorai.retrospect.support.ApiDocumentUtils.getDocumentRequest;
import static choorai.retrospect.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
public class RetrospectRoomDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RetrospectRoomService retrospectRoomService;

    @DisplayName("회고룸 생성 api 문서 테스트")
    @WithMockUser
    @Test
    void create_api() throws Exception {
        // given
        final CreateRequest request = new CreateRequest("1주차 개발 회고", "1주차 동안 부족했던 점, 개선할 점 회고", "KPT", "01:00:00");
        final CreateResponse response = new CreateResponse("17dbc794-c5a8-4b55-bc16-a3a678cae452");

        given(retrospectRoomService.create(any(CreateRequest.class)))
            .willReturn(response);
        // when
        final ResultActions result = this.mockMvc.perform(
            post("/retrospect-room")
                .header(HttpHeaders.AUTHORIZATION, "유저 토큰")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
            .andDo(document("retrospect-room-create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION).description("인증 유저 토큰 정보")
                ),
                requestFields(
                    fieldWithPath("subject").type(JsonFieldType.STRING).description("회고 주제"),
                    fieldWithPath("details").type(JsonFieldType.STRING).description("회고 상세 내용"),
                    fieldWithPath("retrospectType").type(JsonFieldType.STRING).description("회고 종류"),
                    fieldWithPath("timeLimit").type(JsonFieldType.STRING).description("회고 제한 시간")
                ),
                responseFields(
                    fieldWithPath("resultCode").type(JsonFieldType.STRING).description("응답 결과 코드"),
                    fieldWithPath("data.shareLink").type(JsonFieldType.STRING).description("회고 공유 링크")
                )
            ));
    }
}
