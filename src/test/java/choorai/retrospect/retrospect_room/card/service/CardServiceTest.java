package choorai.retrospect.retrospect_room.card.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.support.MockUser;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.service.UserService;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MockUser
class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RetrospectRoomRepository retrospectRoomRepository;


    private RetrospectRoom testRetrospectRoom;


    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // user 세팅
        Long userId = 1L;
        final User currentUser = userService.getCurrentUser();
        final Field id = currentUser.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(currentUser, userId);
        final User save = userRepository.save(currentUser);

        // RetrospectRoom 세팅
        testRetrospectRoom = RetrospectRoom.forSave("회고주제", "회고 상새 내용", "KPT",
                                                    "01:00:00", "shareLink");
        retrospectRoomRepository.save(testRetrospectRoom);
    }

    @Test
    void createCard_success() {
        // given
        final String type = "KEEP";
        final String content = "Keep 내용";
        final CardCreateRequest request = new CardCreateRequest(type, content);

        // when
        final CardResponse response = cardService.createCard(testRetrospectRoom.getId(), request);

        // then
        assertAll(
            () -> assertThat(response.getType()).isEqualTo(type),
            () -> assertThat(response.getContent()).isEqualTo(content)
        );
    }


}
