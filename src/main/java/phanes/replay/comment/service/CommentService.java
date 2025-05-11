package phanes.replay.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.comment.mapper.CommentMapper;
import phanes.replay.gathering.repository.GatheringCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final GatheringCommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentRs> getCommentByGatheringId(Long gatheringId, Pageable pageable) {
        return commentRepository.findByGatheringId(gatheringId, pageable).stream().map(commentMapper::toCommentRs).toList();
    }
}
