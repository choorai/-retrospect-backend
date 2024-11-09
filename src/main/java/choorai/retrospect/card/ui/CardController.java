package choorai.retrospect.card.ui;

import choorai.retrospect.card.entity.dto.CardRequest;
import choorai.retrospect.card.entity.dto.CardResponse;
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
    public BaseResponseDto<CardResponse> createCard(@RequestBody final CardRequest request) {
        CardResponse card = cardService.createCard(request);
        return BaseResponseDto.ofSuccess(card);
    }

    @GetMapping("/{id}")
    public BaseResponseDto<CardResponse> getCardById(@PathVariable Long id) {
        CardResponse card = cardService.getCardResponseById(id);
        return BaseResponseDto.ofSuccess(card);
    }

    @GetMapping("/all")
    public BaseResponseDto<List<CardResponse>> getAllCards() {
        List<CardResponse> cards = cardService.getAllCards();
        return BaseResponseDto.ofSuccess(cards);
    }

    @PostMapping("/update/{id}")
    public BaseResponseDto<CardResponse> updateCard(@PathVariable Long id, @RequestBody CardRequest request) {
        CardResponse updatedCard = cardService.updateCard(id, request);
        return BaseResponseDto.ofSuccess(updatedCard);
    }

    @PostMapping("/delete/{id}")
    public BaseResponseDto<String> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return BaseResponseDto.ofSuccess();
    }
}
