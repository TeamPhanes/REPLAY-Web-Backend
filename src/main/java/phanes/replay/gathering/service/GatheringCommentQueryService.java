package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.repository.GatheringCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringCommentQueryService {

    private final GatheringCommentRepository gatheringCommentRepository;

    public List<GatheringComment> findAllByGatheringId(Long gatheringId) {
        return gatheringCommentRepository.findAllByGatheringId(gatheringId);
    }

    public void deleteAll(List<GatheringComment> gatheringCommentList) {
        gatheringCommentRepository.deleteAll(gatheringCommentList);
    }
}