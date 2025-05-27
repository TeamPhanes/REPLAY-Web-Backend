package phanes.replay.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.comment.dto.request.CommentCreateRq;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.comment.mapper.CommentMapper;
import phanes.replay.exception.CommentNotFoundException;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.persistence.repository.GatheringCommentRepository;
import phanes.replay.gathering.service.GatheringQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final GatheringCommentRepository commentRepository;
    private final GatheringQueryService gatheringQueryService;
    private final UserQueryService userQueryService;
    private final CommentMapper commentMapper;

    public List<CommentRs> getCommentByGatheringId(Long gatheringId, Pageable pageable) {
        List<GatheringComment> gatheringComment = commentRepository.findByGatheringId(gatheringId, pageable);
        Map<Long, List<GatheringComment>> groupedByParent = gatheringComment.stream().collect(Collectors.groupingBy(gc -> Optional.ofNullable(gc.getParentId()).orElse(0L)));
        List<CommentRs> rootComment = groupedByParent.getOrDefault(0L, new ArrayList<>()).stream().map(commentMapper::toCommentRs).toList();
        rootComment.forEach(c -> c.setReComments(groupedByParent.getOrDefault(c.getCommentId(), new ArrayList<>()).stream().map(commentMapper::toReCommentRs).toList()));
        return rootComment;
    }

    public void createComment(Long userId, Long gatheringId, CommentCreateRq commentCreateRq) {
        User user = userQueryService.findByUserId(userId);
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        commentRepository.save(GatheringComment.builder()
                .user(user)
                .gathering(gathering)
                .content(commentCreateRq.getContent())
                .parentId(commentCreateRq.getParentId())
                .build());
    }

    public void updateComment(Long userId, Long commentId, Long gatheringId, String content) {
        GatheringComment comment = commentRepository.findByIdAndGatheringIdAndUserId(commentId, gatheringId, userId).orElseThrow(() -> new CommentNotFoundException("comment not found"));
        comment.updateComment(content);
        commentRepository.save(comment);
    }

    public void deleteComment(Long userId, Long commentId, Long gatheringId) {
        GatheringComment comment = commentRepository.findByIdAndGatheringIdAndUserId(commentId, gatheringId, userId).orElseThrow(() -> new CommentNotFoundException("comment not found"));
        commentRepository.delete(comment);
    }
}
