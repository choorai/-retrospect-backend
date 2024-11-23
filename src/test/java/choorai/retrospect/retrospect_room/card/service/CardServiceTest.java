package choorai.retrospect.retrospect_room.card.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.assertAll;

import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.support.MockUser;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.service.UserService;
=======
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import choorai.retrospect.retrospect_room.card.entity.Card;
import choorai.retrospect.retrospect_room.card.entity.repository.CardRepository;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.card.service.dto.CardUpdateRequest;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import jakarta.transaction.Transactional;
>>>>>>> f5cf9fb (RH-24 : service 테스트 추가)
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
    private CardRepository cardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RetrospectRoomRepository retrospectRoomRepository;

    private RetrospectRoom retrospectRoom;
    private User user;
    private Card card;

    @BeforeEach
    void set() {
        User currentUser = userService.getCurrentUser();
        currentUser = userRepository.save(currentUser);

        retrospectRoom = retrospectRoomRepository.save(
            RetrospectRoom.forSave("주제", "회고 상세 내용", "KPT", "01:00:00", "shareLink"));
        card = cardRepository.save(Card.forSave("KEEP", "Keep 내용", retrospectRoom, user));
    }

    @Test
    void updateCard_success() {
        // given
        final Long cardId = card.getId();
        final Long roomId = retrospectRoom.getId();
        final CardUpdateRequest updateRequest = new CardUpdateRequest("TRY", "Updated Content");

        // when
        final CardResponse updatedCardResponse = cardService.updateCard(roomId, cardId, updateRequest);

        // then
        assertAll(
            () -> assertThat(updatedCardResponse.getType()).isEqualTo("TRY"),
            () -> assertThat(updatedCardResponse.getContent()).isEqualTo("Updated Content")

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

    @Test
    void updateCard_fail_invalidRoomId() {
        // given
        final Long wrongRoomId = 999L;
        final Long cardId = card.getId();
        final CardUpdateRequest updateRequest = new CardUpdateRequest("TRY", "Updated Content");

        // when & then
        assertThatThrownBy(() -> cardService.updateCard(wrongRoomId, cardId, updateRequest))
            .isInstanceOf(CardException.class)
            .hasMessage(CardErrorCode.CARD_IS_NOT_IN_ROOM.getMessage());
    }


    @Test
    void updateCard_fail_cardNotFound() {
        // given
        final Long nonexistentCardId = 999L;
        final Long retrospectRoomId = retrospectRoom.getId();
        final CardUpdateRequest updateRequest = new CardUpdateRequest("TRY", "Updated Content");

        // when & then
        assertThatThrownBy(() -> cardService.updateCard(retrospectRoomId, nonexistentCardId, updateRequest))
            .isInstanceOf(CardException.class)
            .hasMessage(CardErrorCode.CARD_NOT_FOUND_FOR_ID.getMessage());
    }


}
