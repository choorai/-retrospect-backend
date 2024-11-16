package choorai.retrospect.retrospect_room.card.service;

<<<<<<< HEAD
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
=======
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
>>>>>>> 28b3343 (RH-25 : service 테스트 추가)
import static org.junit.jupiter.api.Assertions.assertAll;

import choorai.retrospect.retrospect_room.card.entity.Card;
import choorai.retrospect.retrospect_room.card.entity.repository.CardRepository;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
<<<<<<< HEAD
import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.card.service.dto.CardUpdateRequest;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.support.MockUser;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.service.UserService;
=======
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> 28b3343 (RH-25 : service 테스트 추가)
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD

@SpringBootTest
@MockUser
class CardServiceTest {

=======
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CardServiceTest {


>>>>>>> 28b3343 (RH-25 : service 테스트 추가)
    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RetrospectRoomRepository retrospectRoomRepository;


    private RetrospectRoom retrospectRoom;
    private Card card1;
    private Card card2;
    private Card card3;


    @BeforeEach
    void setUp() {
        cardRepository.deleteAll();

        retrospectRoom = retrospectRoomRepository.save(
            RetrospectRoom.forSave("주제", "회고 상세 내용", "KPT", "01:00:00", "shareLink"));
        User user = userRepository.save(new User("test@test.com", "testsssss", "test"));

        card1 = cardRepository.save(Card.forSave("KEEP", "Keep 내용", retrospectRoom, user));
        card2 = cardRepository.save(Card.forSave("TRY", "Try 내용", retrospectRoom, user));
        card3 = cardRepository.save(Card.forSave("PROBLEM", "Problem 내용", retrospectRoom, user));

    }

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



    @Autowired
    private UserRepository userRepository;

    @Autowired
    RetrospectRoomRepository retrospectRoomRepository;

    private RetrospectRoom retrospectRoom;
    private Card card;

    @BeforeEach
    void set() {
        User currentUser = userService.getCurrentUser();
        currentUser = userRepository.save(currentUser);

        retrospectRoom = retrospectRoomRepository.save(
            RetrospectRoom.forSave("주제", "회고 상세 내용", "KPT", "01:00:00", "shareLink"));
        card = cardRepository.save(Card.forSave("KEEP", "Keep 내용", retrospectRoom, currentUser));
    }

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
