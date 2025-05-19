package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.persistence.repository.GatheringCommentRepository;

@Service
@RequiredArgsConstructor
public class GatheringCommentQueryService {

    private final GatheringCommentRepository gatheringCommentRepository;

    public Page<GatheringComment> getMyComment(Long userId, Pageable pageable) {
        return gatheringCommentRepository.findByUserId(userId, pageable);
    }
}
