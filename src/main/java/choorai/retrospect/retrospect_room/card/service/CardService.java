package choorai.retrospect.retrospect_room.card.service;


import choorai.retrospect.retrospect_room.card.entity.Card;
import choorai.retrospect.retrospect_room.card.entity.repository.CardRepository;
import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.service.RetrospectRoomService;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.service.UserService;
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
        validateCardInRoom(retrospectRoomId, card);
        card.update(request.getType(), request.getContent());
        return CardResponse.of(card);
    }

    private Card getCardById(final Long id) {
        return cardRepository.findById(id)
            .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND_FOR_ID));
    }

    private void validateCardInRoom(final Long retrospectRoomId, final Card card) {
        if (!card.isInRoom(retrospectRoomId)) {
            throw new CardException(CardErrorCode.CARD_IS_NOT_IN_ROOM);
        }
    }

}
