package phanes.replay.comment.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.comment.dto.request.CommentCreateRq;
import phanes.replay.comment.dto.request.CommentUpdateRq;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentRs> getCommentList(@RequestParam Long gatheringId, @RequestParam int offset, @RequestParam int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "createdAt"));
        return commentService.findAllByGatheringId(gatheringId, pageRequest);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createComment(@AuthenticationPrincipal Long userId, @RequestParam Long gatheringId, @RequestBody CommentCreateRq commentCreateRq) {
        commentService.createComment(userId, gatheringId, commentCreateRq);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{commentId}")
    public void updateComment(@AuthenticationPrincipal Long userId, @PathVariable Long commentId, @RequestParam Long gatheringId, @RequestBody CommentUpdateRq commentUpdateRq) {
        commentService.updateComment(userId, commentId, gatheringId, commentUpdateRq.getContent());
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{commentId}")
    public void deleteComment(@AuthenticationPrincipal Long userId, @PathVariable Long commentId, @RequestParam Long gatheringId) {
        commentService.deleteComment(userId, commentId, gatheringId);
    }
}
