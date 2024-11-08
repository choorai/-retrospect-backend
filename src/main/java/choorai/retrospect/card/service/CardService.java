package choorai.retrospect.card.service;

import choorai.retrospect.card.entity.Card;
import choorai.retrospect.card.entity.dto.CardRequest;
import choorai.retrospect.card.entity.repository.CardRepository;
import choorai.retrospect.card.exception.CardErrorCode;
import choorai.retrospect.card.exception.CardException;
import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.service.UserService;
import java.util.List;
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
    public Card createCard(CardRequest cardRequest) {
        validateCardRequest(cardRequest);
        final User currentUser = userService.getCurrentUser();
        // TODO : 교체
        final RetrospectRoom retrospectRoom = retrospectRoomService.getRetrospectRoomBy(id);

        Card card = Card.forSave(cardRequest.getType(),
                                 cardRequest.getContent(),
                                 retrospectRoom,
                                 currentUser);

        currentUser.addCard(card);
        retrospectRoom.addCard(card);
        return cardRepository.save(card);
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id)
            .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND_FOR_ID));
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Transactional
    public Card updateCard(Long id, CardRequest request) {
        validateCardRequest(request);
        Card card = getCardById(id);
        card.setType(request.getType());
        card.setContent(request.getContent());
        return cardRepository.save(card);
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
