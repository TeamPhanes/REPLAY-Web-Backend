package phanes.replay.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentRs> commentList(@RequestParam Long gatheringId, @RequestParam int offset, @RequestParam int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return commentService.getCommentByGatheringId(gatheringId, pageRequest);
    }
}
