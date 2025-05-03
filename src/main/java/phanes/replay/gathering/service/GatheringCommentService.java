package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.repository.GatheringCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringCommentService {

    private final GatheringCommentRepository gatheringCommentRepository;

    public List<GatheringComment> getMyComment(Long userId, Pageable pageable) {
        return gatheringCommentRepository.findByUserId(userId, pageable);
    }
}
