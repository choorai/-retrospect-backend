package choorai.retrospect.retrospect_room.ui;

import choorai.retrospect.global.response.BaseResponseDto;
import choorai.retrospect.retrospect_room.card.service.CardService;
import choorai.retrospect.retrospect_room.card.service.dto.CardCreateRequest;
import choorai.retrospect.retrospect_room.card.service.dto.CardResponse;
import choorai.retrospect.retrospect_room.card.service.dto.CardUpdateRequest;
import choorai.retrospect.retrospect_room.service.RetrospectRoomService;
import choorai.retrospect.retrospect_room.service.dto.CreateRequest;
import choorai.retrospect.retrospect_room.service.dto.CreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/retrospect-room")
@RequiredArgsConstructor
@RestController
public class RetrospectRoomController {

    private final RetrospectRoomService retrospectRoomService;
    private final CardService cardService;

    @PostMapping()
    public BaseResponseDto<CreateResponse> createRoom(@RequestBody final CreateRequest request) {
        final CreateResponse response = retrospectRoomService.create(request);
        return BaseResponseDto.ofSuccess(response);
    }

    @PatchMapping("/{retrospectRoomId}/cards/{cardId}")
    public BaseResponseDto<CardResponse> updateCard(@PathVariable final Long retrospectRoomId,
                                                    @PathVariable final Long cardId,
                                                    @RequestBody final CardUpdateRequest request) {
        final CardResponse updatedCard = cardService.updateCard(retrospectRoomId, cardId, request);
        return BaseResponseDto.ofSuccess(updatedCard);
    }

    @PostMapping("/{retrospectRoomId}/createCard")
    public BaseResponseDto<CardResponse> createCard(@PathVariable final Long retrospectRoomId,
                                                    @RequestBody final CardCreateRequest request) {
        CardResponse card = cardService.createCard(retrospectRoomId, request);
        return BaseResponseDto.ofSuccess(card);

    }

    @GetMapping("/{retrospectRoomId}/cards/{cardId}")
    public BaseResponseDto<CardResponse> getCardById(@PathVariable final Long retrospectRoomId,
                                                     @PathVariable final Long cardId) {
        final CardResponse card = cardService.getCardResponseById(retrospectRoomId, cardId);
        return BaseResponseDto.ofSuccess(card);
    }

    @GetMapping("/{retrospectRoomId}/cards")
    public BaseResponseDto<List<CardResponse>> getAllCards(@PathVariable final Long retrospectRoomId) {
        final List<CardResponse> cards = cardService.getAllCards(retrospectRoomId);
        return BaseResponseDto.ofSuccess(cards);
    }

    @DeleteMapping("/{retrospectRoomId}/cards/{cardId}")
    public BaseResponseDto<String> deleteCard(@PathVariable final Long retrospectRoomId,
                                              @PathVariable final Long cardId) {
        cardService.deleteCard(retrospectRoomId, cardId);
        return BaseResponseDto.ofSuccess();
    }
}
