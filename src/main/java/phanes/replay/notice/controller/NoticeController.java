package phanes.replay.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.notice.dto.request.NoticeRq;
import phanes.replay.notice.dto.response.NoticeDetailRs;
import phanes.replay.notice.dto.response.NoticeRs;
import phanes.replay.notice.service.NoticeService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public Page<NoticeRs> getNoticeList(@PageableDefault Pageable pageable) {
        return noticeService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public NoticeDetailRs getNoticeDetail(@PathVariable Long id) {
        return noticeService.findByNoticeId(id);
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getTempImageUrl(@RequestPart MultipartFile image) {
        return Map.of("image", noticeService.saveTempImage(image));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void saveNotice(@AuthenticationPrincipal Long userId, @RequestBody NoticeRq noticeRq) {
        noticeService.saveNotice(userId, noticeRq);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public void updateNotice(@AuthenticationPrincipal Long userId, @RequestParam Long id, @RequestBody String content) {
        noticeService.updateNotice(userId, id, content);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteNotice(@AuthenticationPrincipal Long userId, @PathVariable Long id) {
        noticeService.delete(userId, id);
    }
}