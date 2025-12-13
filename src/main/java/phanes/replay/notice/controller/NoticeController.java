package phanes.replay.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.notice.dto.response.NoticeContentRs;
import phanes.replay.notice.dto.response.NoticeRs;
import phanes.replay.notice.service.NoticeService;

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
    public NoticeContentRs getNoticeDetail(@PathVariable Long id) {
        return noticeService.findByNoticeId(id);
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTempImageUrl(@RequestPart MultipartFile image) {
        return noticeService.saveTempImage(image);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void saveNotice() {

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public void updateNotice() {

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable Long id) {
        noticeService.delete(id);
    }
}