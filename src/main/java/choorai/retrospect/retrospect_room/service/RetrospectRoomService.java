package choorai.retrospect.retrospect_room.service;

import choorai.retrospect.retrospect_room.entity.RetrospectRoom;
import choorai.retrospect.retrospect_room.entity.repository.RetrospectRoomRepository;
import choorai.retrospect.retrospect_room.excpetion.RetrospectRoomErrorCode;
import choorai.retrospect.retrospect_room.excpetion.RetrospectRoomException;
import choorai.retrospect.retrospect_room.service.dto.CreateRequest;
import choorai.retrospect.retrospect_room.service.dto.CreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RetrospectRoomService {

    private final RetrospectRoomRepository retrospectRoomRepository;
    private final ShareLinkCreateStrategy shareLinkCreateStrategy;

    @Transactional
    public CreateResponse create(final CreateRequest request) {
        final String shareLink = shareLinkCreateStrategy.createShareLink();
        final RetrospectRoom retrospectRoom = RetrospectRoom.forSave(request.getSubject(), request.getDetails(),
                                                                     request.getRetrospectType(),
                                                                     request.getTimeLimit(), shareLink);
        retrospectRoomRepository.save(retrospectRoom);

        return new CreateResponse(shareLink);
    }

    public RetrospectRoom findById(final long id) {
        return retrospectRoomRepository.findWithCardsById(id)
            .orElseThrow(() -> new RetrospectRoomException(RetrospectRoomErrorCode.RETROSPECT_ROOM_NOT_FOUND_FOR_ID));
    }
}
