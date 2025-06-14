package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.persistence.repository.GatheringCommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GatheringCommentQueryService {

    private final GatheringCommentRepository gatheringCommentRepository;

    public List<GatheringComment> findByUserId(Long userId, Pageable pageable) {
        return gatheringCommentRepository.findByUserId(userId, pageable);
    }

    public List<GatheringComment> findAllByGatheringId(Long gatheringId) {
        return gatheringCommentRepository.findAllByGatheringId(gatheringId);
    }

    public List<GatheringComment> findByGatheringId(Long gatheringId, Pageable pageable) {
        return gatheringCommentRepository.findByGatheringId(gatheringId, pageable);
    }

    public Optional<GatheringComment> findByIdAndGatheringIdAndUserId(Long commentId, Long gatheringId, Long userId) {
        return gatheringCommentRepository.findByIdAndGatheringIdAndUserId(commentId, gatheringId, userId);
    }

    public Long countByUserId(Long userId) {
        return gatheringCommentRepository.countByUserId(userId);
    }

    public void save(GatheringComment gatheringComment) {
        gatheringCommentRepository.save(gatheringComment);
    }

    public void delete(GatheringComment comment) {
        gatheringCommentRepository.delete(comment);
    }

    public void deleteAll(List<GatheringComment> gatheringCommentList) {
        gatheringCommentRepository.deleteAll(gatheringCommentList);
    }
}
