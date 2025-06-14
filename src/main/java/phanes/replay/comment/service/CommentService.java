package phanes.replay.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.comment.dto.mapper.CommentMapper;
import phanes.replay.comment.dto.request.CommentCreateRq;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.exception.CommentNotFoundException;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.service.GatheringCommentQueryService;
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

    private final GatheringCommentQueryService gatheringCommentQueryService;
    private final GatheringQueryService gatheringQueryService;
    private final UserQueryService userQueryService;
    private final CommentMapper commentMapper;

    public List<CommentRs> getCommentByGatheringId(Long gatheringId, Pageable pageable) {
        List<GatheringComment> gatheringComment = gatheringCommentQueryService.findByGatheringId(gatheringId, pageable);
        Map<Long, List<GatheringComment>> groupedByParent = gatheringComment.stream().collect(Collectors.groupingBy(gc -> Optional.ofNullable(gc.getParentId()).orElse(0L)));
        List<CommentRs> rootComment = groupedByParent.getOrDefault(0L, new ArrayList<>()).stream().map(commentMapper::toCommentRs).toList();
        rootComment.forEach(c -> c.setReComments(groupedByParent.getOrDefault(c.getCommentId(), new ArrayList<>()).stream().map(commentMapper::toReCommentRs).toList()));
        return rootComment;
    }

    public void createComment(Long userId, Long gatheringId, CommentCreateRq commentCreateRq) {
        User user = userQueryService.findById(userId);
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        gatheringCommentQueryService.save(GatheringComment.builder()
                .user(user)
                .gathering(gathering)
                .content(commentCreateRq.getContent())
                .parentId(commentCreateRq.getParentId())
                .build());
    }

    public void updateComment(Long userId, Long commentId, Long gatheringId, String content) {
        GatheringComment comment = gatheringCommentQueryService.findByIdAndGatheringIdAndUserId(commentId, gatheringId, userId).orElseThrow(() -> new CommentNotFoundException(String.format("user: %d, gathering: %d, comment: %d not found", userId, gatheringId, commentId)));
        comment.updateComment(content);
        gatheringCommentQueryService.save(comment);
    }

    public void deleteComment(Long userId, Long commentId, Long gatheringId) {
        GatheringComment comment = gatheringCommentQueryService.findByIdAndGatheringIdAndUserId(commentId, gatheringId, userId).orElseThrow(() -> new CommentNotFoundException(String.format("user: %d, gathering: %d, comment: %d not found", userId, gatheringId, commentId)));
        gatheringCommentQueryService.delete(comment);
    }
}
