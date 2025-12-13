package phanes.replay.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.common.s3.S3Repository;
import phanes.replay.notice.domain.Notice;
import phanes.replay.notice.domain.NoticeContent;
import phanes.replay.notice.dto.response.NoticeContentRs;
import phanes.replay.notice.dto.response.NoticeRs;
import phanes.replay.notice.mapper.NoticeMapper;
import phanes.replay.utils.FileUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeQueryService noticeQueryService;
    private final NoticeContentQueryService noticeContentQueryService;
    private final NoticeMapper noticeMapper;
    private final S3Repository s3Repository;

    public Page<NoticeRs> findAll(Pageable pageable) {
        Page<Notice> noticeList = noticeQueryService.findAll(pageable);
        List<NoticeRs> contents = noticeList.stream().map(noticeMapper::toNoticeRs).toList();
        return new PageImpl<>(contents, pageable, noticeList.getTotalElements());
    }

    public String saveTempImage(MultipartFile image) {
        String extension = FileUtils.getExtension(image.getOriginalFilename());
        return s3Repository.uploadImage("tmp/" + UUID.randomUUID() + "." + extension, image);
    }

    @Transactional
    public void delete(Long id) {
        Notice notice = noticeQueryService.findById(id);
        NoticeContent noticeContent = noticeContentQueryService.findByNoticeId(id);
        noticeContentQueryService.delete(noticeContent);
        noticeQueryService.delete(notice);
    }

    public NoticeContentRs findByNoticeId(Long id) {
        NoticeContent noticeContent = noticeContentQueryService.findByNoticeId(id);
        return noticeMapper.toNoticeContentRs(noticeContent);
    }
}