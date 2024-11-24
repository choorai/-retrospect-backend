package choorai.retrospect.retrospect_room.card.service;


import choorai.retrospect.retrospect_room.card.entity.Card;
import choorai.retrospect.retrospect_room.card.entity.repository.CardRepository;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.card.service.dto.CardUpdateRequest;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.service.RetrospectRoomService;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final RetrospectRoomService retrospectRoomService;
    private final UserService userService;

    public CardResponse getCardResponseById(final Long retrospectRoomId, final Long id) {
        final Card card = getCardById(id);
        validateCardInRoom(retrospectRoomId, card);
        return CardResponse.of(card);
    }

    private void validateCardInRoom(final Long retrospectRoomId, final Card card) {
        if (!card.isInRoom(retrospectRoomId)) {
            throw new CardException(CardErrorCode.CARD_IS_NOT_IN_ROOM);
        }
    }

    public List<CardResponse> getAllCards(final Long retrospectRoomId) {
        List<Card> cards = cardRepository.findByRetrospectRoom_Id(retrospectRoomId);
        return cards.stream()
            .map(CardResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public CardResponse createCard(final Long retrospectRoomId, final CardCreateRequest cardRequest) {
        final User currentUser = userService.getCurrentUserWithCards();
        final RetrospectRoom retrospectRoom = retrospectRoomService.findById(retrospectRoomId);

        final Card card = Card.forSave(cardRequest.getType(), cardRequest.getContent(), retrospectRoom, currentUser);
        cardRepository.save(card);

        return CardResponse.of(card);
    }

    @Transactional
    public CardResponse updateCard(final Long retrospectRoomId, final Long cardId, final CardUpdateRequest request) {
        final Card card = getCardById(cardId);
        card.update(retrospectRoomId, request.getType(), request.getContent());
        return CardResponse.of(card);
    }

    private Card getCardById(final Long id) {
        return cardRepository.findById(id)
            .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND_FOR_ID));
    }


    @Transactional
    public void deleteCard(final Long retrospectRoomId, final Long cardId) {
        final Card card = getCardById(cardId);
        validateCardInRoom(retrospectRoomId, card);

        final User user = getCardAuthor(card);
        user.removeCardById(card.getId());
        final RetrospectRoom retrospectRoom = card.getRetrospectRoom();
        retrospectRoom.removeCardById(card.getId());

        cardRepository.delete(card);
    }

    private User getCardAuthor(final Card card) {
        final User author = card.getUser();
        final Long authorId = author.getId();
        final Long currentUserId = userService.getCurrentUser().getId();
        if (!authorId.equals(currentUserId)) {
            throw new CardException(CardErrorCode.CARD_NOT_AUTHORED_BY_USER);
        }
        return author;
    }

}
