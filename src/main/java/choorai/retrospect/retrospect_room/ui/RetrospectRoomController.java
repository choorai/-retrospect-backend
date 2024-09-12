package choorai.retrospect.retrospect_room.ui;

import choorai.retrospect.retrospect_room.service.RetrospectRoomService;
import choorai.retrospect.retrospect_room.service.dto.CreateRequest;
import choorai.retrospect.retrospect_room.service.dto.CreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/retrospect-room")
@RequiredArgsConstructor
@RestController
public class RetrospectRoomController {

    private final RetrospectRoomService retrospectRoomService;

    @PostMapping
    public ResponseEntity<CreateResponse> createRoom(@RequestBody CreateRequest request) {
        final CreateResponse response = retrospectRoomService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
