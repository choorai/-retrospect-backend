package choorai.retrospect.retrospect_room.card.service;

import choorai.retrospect.retrospect_room.card.entity.Card;
import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.card.entity.repository.CardRepository;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
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

    @Transactional
    public CardResponse createCard(Long retrospectRoomId, CardCreateRequest cardRequest) {
        final User currentUser = userService.getCurrentUserWithCards();
        final RetrospectRoom retrospectRoom = retrospectRoomService.findById(retrospectRoomId);

        Card card = Card.forSave(cardRequest.getType(),
                                 cardRequest.getContent(),
                                 retrospectRoom,
                                 currentUser);

        retrospectRoom.addCard(card);
        currentUser.addCard(card);
        cardRepository.save(card);

        return CardResponse.of(card);
    }

    private Card getCardById(Long id) {
        return cardRepository.findById(id)
            .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND_FOR_ID));
    }

    public CardResponse getCardResponseById(Long retrospectRoomId, Long id) {
        final Card card = getCardById(id);
        validateCardInRoom(retrospectRoomId, card);
        return CardResponse.of(card);
    }

    private void validateCardInRoom(Long retrospectRoomId, Card card) {
        if (!card.isInRoom(retrospectRoomId)) {
            throw new CardException(CardErrorCode.CARD_IS_NOT_IN_ROOM);
        }
    }

    public List<CardResponse> getAllCards(Long retrospectRoomId) {
        List<Card> cards = cardRepository.findByRetrospectRoom_Id(retrospectRoomId);
        return cards.stream()
            .map(CardResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public CardResponse updateCard(Long retrospectRoomId, Long cardUd, CardUpdateRequest request) {
        Card card = getCardById(cardUd);
        validateCardInRoom(retrospectRoomId, card);
        updateCard(request, card);

        return CardResponse.of(card);
    }

    private void updateCard(CardUpdateRequest request, Card card) {
        if (request.getType() != null && !request.getType().isEmpty()) {
            card.setType(request.getType());
        }
        if (request.getContent() != null && !request.getContent().isEmpty()) {
            card.setContent(request.getContent());
        }
    }

    @Transactional
    public void deleteCard(Long retrospectRoomId, Long cardId) {
        Card card = getCardById(cardId);
        validateCardInRoom(retrospectRoomId, card);

        final User user = card.getUser();
        user.removeCardById(card.getId());
        final RetrospectRoom retrospectRoom = card.getRetrospectRoom();
        retrospectRoom.removeCardById(card.getId());

        cardRepository.delete(card);
    }


}
