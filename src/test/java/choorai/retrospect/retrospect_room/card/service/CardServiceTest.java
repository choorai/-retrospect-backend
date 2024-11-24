package choorai.retrospect.retrospect_room.card.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

import choorai.retrospect.retrospect_room.card.entity.Card;
import choorai.retrospect.retrospect_room.card.entity.repository.CardRepository;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.card.service.dto.CardUpdateRequest;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.support.MockUser;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private CardRepository cardRepository;

    @Autowired
    private RetrospectRoomRepository retrospectRoomRepository;

    private RetrospectRoom retrospectRoom;
    private User currentUser;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    void set() {
        currentUser = userRepository.save(userService.getCurrentUser());

        retrospectRoom = retrospectRoomRepository.save(
            RetrospectRoom.forSave("주제", "회고 상세 내용", "KPT", "01:00:00", "shareLink"));
        card1 = cardRepository.save(Card.forSave("KEEP", "Keep 내용", retrospectRoom, currentUser));
        card2 = cardRepository.save(Card.forSave("TRY", "Try 내용", retrospectRoom, currentUser));
        card3 = cardRepository.save(Card.forSave("PROBLEM", "Problem 내용", retrospectRoom, currentUser));

    }

    @DisplayName("Card 생성 성공 테스트")
    @Test
    void createCard_success() {
        // given
        final String type = "KEEP";
        final String content = "Keep 내용";
        final CardCreateRequest request = new CardCreateRequest(type, content);

        // when
        final CardResponse response = cardService.createCard(retrospectRoom.getId(), request);

        // then
        assertAll(
            () -> assertThat(response.getType()).isEqualTo(type),
            () -> assertThat(response.getContent()).isEqualTo(content)
        );
    }

    @DisplayName("Card 수정 성공 테스트")
    @Test
    void updateCard_success() {
        // given
        final Long cardId = card1.getId();
        final Long roomId = retrospectRoom.getId();
        final CardUpdateRequest updateRequest = new CardUpdateRequest("TRY", "Updated Content");

        // when
        final CardResponse updatedCardResponse = cardService.updateCard(roomId, cardId, updateRequest);

        // then
        assertAll(
            () -> assertThat(updatedCardResponse.getType()).isEqualTo("TRY"),
            () -> assertThat(updatedCardResponse.getContent()).isEqualTo("Updated Content")
        );
    }

    @DisplayName("retrospectRoom에 속하지 않는 card을 수정할 경우 예외가 발생한다.")
    @Test
    void updateCard_fail_invalidRoomId() {
        // given
        final Long wrongRoomId = 999L;
        final Long cardId = card1.getId();
        final CardUpdateRequest updateRequest = new CardUpdateRequest("TRY", "Updated Content");

        // when & then
        assertThatThrownBy(() -> cardService.updateCard(wrongRoomId, cardId, updateRequest))
            .isInstanceOf(CardException.class)
            .hasMessage(CardErrorCode.CARD_IS_NOT_IN_ROOM.getMessage());
    }

    @DisplayName("존재하지 않는 card을 수정할 경우 예외가 발생한다.")
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

    @DisplayName("Card 조회 성공 테스트")
    @Test
    void getCardResponseById_ValidCard_Success() {
        // given
        final Long card1Id = card1.getId();
        final Long retrospectRoomId = retrospectRoom.getId();

        // when
        final CardResponse response = cardService.getCardResponseById(retrospectRoomId, card1Id);

        // then
        assertAll(
            () -> assertThat(response.getType()).isEqualTo(card1.getType().toString()),
            () -> assertThat(response.getContent()).isEqualTo(card1.getContent())
        );
    }

    @DisplayName("잘못된 retrospectRoomId을 입력하면 예외가 발생한다.")
    @Test
    void getCardResponseById_InvalidRoom_ThrowsException() {
        // given
        final Long card1Id = card1.getId();
        final Long wrongRetrospectRoomId = 999L;

        // when
        // then
        assertThatThrownBy(() -> cardService.getCardResponseById(wrongRetrospectRoomId, card1Id))
            .isInstanceOf(CardException.class)
            .hasMessageContaining(CardErrorCode.CARD_IS_NOT_IN_ROOM.getMessage());
    }

    @DisplayName("retrospectRoom에 속한 모든 Card 조회 성공 테스트")
    @Test
    void getAllCards_ValidRoom_Success() {
        // given
        List<Card> expectedCards = Arrays.asList(card1, card2, card3);
        final Long retrospectRoomId = retrospectRoom.getId();

        // when
        List<CardResponse> cards = cardService.getAllCards(retrospectRoomId);

        // then
        assertThat(cards)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .isEqualTo(
                expectedCards.stream()
                    .map(CardResponse::of)
                    .collect(Collectors.toList())
            );
    }

    @DisplayName("Card 삭제 성공 테스트")
    @Transactional
    @Test
    void testDeleteCard_Success() {
        // given
        // when
        cardService.deleteCard(retrospectRoom.getId(), card1.getId());
        final List<Card> userCards = userRepository.findById(currentUser.getId()).get().getCards();
        final List<Card> retrospectRoomCards = retrospectRoomRepository.findById(retrospectRoom.getId()).get()
            .getCards();


        // then
        assertAll(
            () -> assertFalse(cardRepository.findById(card1.getId()).isPresent()),
            () -> assertFalse(userCards.contains(card1)),
            () -> assertFalse(retrospectRoomCards.contains(card1))
        );
    }

    @DisplayName("retrospectRoom에 속하지 않은 card을 삭제할 경우 예외가 발생한다.")
    @Test
    void testDeleteCard_CardNotInRoom() {
        // given
        final Long wrongRetrospectRoomId = 999L;

        // when
        // then
        assertThatThrownBy(() -> cardService.deleteCard(wrongRetrospectRoomId, card1.getId()))
            .isInstanceOf(CardException.class)
            .hasMessageContaining(CardErrorCode.CARD_IS_NOT_IN_ROOM.getMessage());
    }

    @DisplayName("존재하지 않는 card을 삭제할 경우 예외가 발생한다.")
    @Test
    void testDeleteCard_CardNotFound() {
        // given
        final Long wrongCardId = 999L;

        // when
        // then
        assertThatThrownBy(() -> cardService.deleteCard(retrospectRoom.getId(), wrongCardId))
            .isInstanceOf(CardException.class)
            .hasMessageContaining(CardErrorCode.CARD_NOT_FOUND_FOR_ID.getMessage());
    }

    @DisplayName("잘못된 author의 card을 삭제할 경우 예외가 발생한다.")
    @Test
    void testDeleteCard_AuthorError() {
        // given
        final User anotherUser = userRepository.save(new User("ttt@ttt.com", "pppppp","테스터2"));
        card1 = cardRepository.save(Card.forSave("KEEP", "Keep 내용", retrospectRoom, anotherUser));
        // when
        // then
        assertThatThrownBy(() -> cardService.deleteCard(retrospectRoom.getId(), card1.getId()))
            .isInstanceOf(CardException.class)
            .hasMessageContaining(CardErrorCode.CARD_NOT_AUTHORED_BY_USER.getMessage());
    }
}
