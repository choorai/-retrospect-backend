package choorai.retrospect.card.ui;

import choorai.retrospect.card.entity.Card;
import choorai.retrospect.card.entity.dto.CardRequest;
import choorai.retrospect.card.service.CardService;
import choorai.retrospect.global.response.BaseResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cards")
@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardService cardService;

    @PostMapping("/create")
    public BaseResponseDto<Card> createCard(@RequestBody final CardRequest request) {
        Card card = cardService.createCard(request);
        return BaseResponseDto.ofSuccess(card);
    }

    @GetMapping("/{id}")
    public BaseResponseDto<Card> getCardById(@PathVariable Long id) {
        Card card = cardService.getCardById(id);
        return BaseResponseDto.ofSuccess(card);
    }

    @GetMapping
    public BaseResponseDto<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return BaseResponseDto.ofSuccess(cards);
    }

    @PostMapping("/update/{id}")
    public BaseResponseDto<Card> updateCard(@PathVariable Long id, @RequestBody CardRequest request) {
        Card updatedCard = cardService.updateCard(id, request);
        return BaseResponseDto.ofSuccess(updatedCard);
    }

    @PostMapping("/delete/{id}")
    public BaseResponseDto<String> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return BaseResponseDto.ofSuccess();
    }
}
