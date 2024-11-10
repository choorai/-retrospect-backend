package choorai.retrospect.card.service;

import choorai.retrospect.card.entity.Card;
import choorai.retrospect.card.service.dto.CardRequest;
import choorai.retrospect.card.service.dto.CardResponse;
import choorai.retrospect.card.entity.repository.CardRepository;
import choorai.retrospect.card.exception.CardErrorCode;
import choorai.retrospect.card.exception.CardException;
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
    public CardResponse createCard(CardRequest cardRequest) {
        validateCardRequest(cardRequest);
        final User currentUser = userService.getCurrentUserWithCards();
        final RetrospectRoom retrospectRoom = retrospectRoomService.findById(cardRequest.getRetrospectRoomId());

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

    public CardResponse getCardResponseById(Long id) {
        final Card card = getCardById(id);
        return CardResponse.of(card);
    }

    public List<CardResponse> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
            .map(CardResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public CardResponse updateCard(Long id, CardRequest request) {
        Card card = getCardById(id);
        if (request.getType() != null && !request.getType().isEmpty()) {
            card.setType(request.getType());
        }
        if (request.getContent() != null && !request.getContent().isEmpty()) {
            card.setContent(request.getContent());
        }

        return CardResponse.of(card);
    }

    @Transactional
    public void deleteCard(Long id) {
        Card card = getCardById(id);
        final User user = card.getUser();
        user.removeCardById(card.getId());
        final RetrospectRoom retrospectRoom = card.getRetrospectRoom();
        retrospectRoom.removeCardById(card.getId());

        cardRepository.delete(card);
    }

    private void validateCardRequest(CardRequest request) {
        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new CardException(CardErrorCode.CONTENT_IS_NOT_NULL);
        }
        if (request.getType() == null || request.getType().isEmpty()) {
            throw new CardException(CardErrorCode.TYPE_IS_NOT_NULL);
        }
    }
}
