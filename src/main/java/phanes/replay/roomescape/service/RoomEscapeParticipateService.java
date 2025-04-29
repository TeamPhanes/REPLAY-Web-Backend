package phanes.replay.roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.roomescape.repository.RoomEscapeParticipateRepository;

@Service
@RequiredArgsConstructor
public class RoomEscapeParticipateService {

    private final RoomEscapeParticipateRepository roomEscapeParticipateRepository;

    public Long getTotalRoomEscapeCount(Long userId) {
        return roomEscapeParticipateRepository.countByUserId(userId);
    }
}
