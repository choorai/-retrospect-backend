package choorai.retrospect.retrospect_room.service;

import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.retrospect_room.service.dto.CreateRequest;
import choorai.retrospect.retrospect_room.service.dto.CreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RetrospectRoomServiceTest {

    @Autowired
    private RetrospectRoomRepository retrospectRoomRepository;

    private RetrospectRoomService retrospectRoomService;

    @BeforeEach
    void setting() {
        retrospectRoomService = new RetrospectRoomService(retrospectRoomRepository, new FixShareLinkStrategy());
    }

    @DisplayName("회고룸을 생성한다.")
    @Test
    void create_retrospect_room() {
        // given
        final CreateRequest request = new CreateRequest("회고 주제", "회고 상새 내용", "KPT", "01:00:00");

        // when
        final CreateResponse result = retrospectRoomService.create(request);

        // then
        assertThat(result.getShareLink()).isEqualTo("shareLink");
    }
}
