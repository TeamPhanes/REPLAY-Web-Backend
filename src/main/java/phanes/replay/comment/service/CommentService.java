package phanes.replay.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.comment.dto.request.CommentCreateRq;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.comment.mapper.CommentMapper;
import phanes.replay.exception.GatheringNotFoundException;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.repository.GatheringCommentRepository;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final GatheringCommentRepository commentRepository;
    private final GatheringRepository gatheringRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public List<CommentRs> getCommentByGatheringId(Long gatheringId, Pageable pageable) {
        return commentRepository.findByGatheringId(gatheringId, pageable).stream().map(commentMapper::toCommentRs).toList();
    }

    public void createComment(Long userId, Long gatheringId, CommentCreateRq commentCreateRq) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(() -> new GatheringNotFoundException("Gathering Not Found"));
        commentRepository.save(GatheringComment.builder()
                .user(user)
                .gathering(gathering)
                .content(commentCreateRq.getContent())
                .parentId(commentCreateRq.getParentId())
                .build());
    }
}
