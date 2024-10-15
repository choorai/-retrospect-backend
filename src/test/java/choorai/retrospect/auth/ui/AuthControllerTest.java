package choorai.retrospect.auth.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import choorai.retrospect.auth.entity.dto.LoginRequest;
import choorai.retrospect.auth.entity.dto.LoginResponse;
import choorai.retrospect.auth.entity.dto.LogoutRequest;
import choorai.retrospect.auth.entity.dto.ReissueTokenRequest;
import choorai.retrospect.auth.entity.dto.ReissueTokenResponse;
import choorai.retrospect.auth.service.AuthService;
import choorai.retrospect.auth.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@WebMvcTest(AuthController.class) // TODO : jwtAuthFilter 생성이 안된다고 예외 발생 -> 추후에 테스트 느려지면 수정하기
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(document("{method-name}"))
            .build();
    }

    @Test
    void loginTest() throws Exception {
        // given
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@naver.com");
        loginRequest.setPassword("afdsa123");

        final LoginResponse loginResponse = new LoginResponse("eyJhbGciOiJIUz~~", "TkekZsO63m2UcwJ6egbT~~");

        given(authService.login(any(LoginRequest.class)))
            .willReturn(loginResponse);
        // when
        final ResultActions result = this.mockMvc.perform(post("/auth/login")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(loginRequest)));
        // then
        result.andExpect(status().isOk())
            .andDo(document("auth-login",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                            ),
                            responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("access token 값"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("refresh token 값")
                            )));
    }

    @Test
    void reissueTest() throws Exception {
        // given
        final ReissueTokenRequest reissueTokenRequest = new ReissueTokenRequest();
        reissueTokenRequest.setRefreshToken("TkekZsO63m2UcwJ6egbT~~");

        final ReissueTokenResponse reissueTokenResponse = new ReissueTokenResponse("eyJhbGciOiJIUz~~");

        given(authService.reissueAccessToken(any(String.class)))
            .willReturn(reissueTokenResponse);
        // when
        final ResultActions result = this.mockMvc.perform(post("/auth/reissue")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(
                                                                  reissueTokenRequest)));
        // then
        result.andExpect(status().isOk())
            .andDo(document("auth-refresh",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("refresh token 값")
                            ),
                            responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("access token 값")
                            )));
    }

    @Test
    void logoutTest() throws Exception {
        // given
        final LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRefreshToken("TkekZsO63m2UcwJ6egbT~~");

        // when
        final ResultActions result = this.mockMvc.perform(post("/auth/logout")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(logoutRequest)));
        // then
        result.andExpect(status().isOk())
            .andDo(document("auth-logout",
                            preprocessRequest(prettyPrint()),
                            requestFields(
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("refresh token 값")
                            ),
                            responseFields(
                                fieldWithPath("").description("로그아웃 성공 메시지 (LOGOUT SUCCESS)")
                            )
            )); // TODO : 공통 response DTO 반영하고 바꿀 때 한꺼번에 바꾸기
    }
}
