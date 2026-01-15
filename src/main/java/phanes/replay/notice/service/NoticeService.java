package phanes.replay.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.config.properties.S3Properties;
import phanes.replay.notice.domain.Notice;
import phanes.replay.notice.domain.NoticeContent;
import phanes.replay.notice.domain.NoticeImage;
import phanes.replay.notice.domain.enums.Status;
import phanes.replay.notice.dto.request.NoticeRq;
import phanes.replay.notice.dto.response.NoticeDetailRs;
import phanes.replay.notice.dto.response.NoticeRs;
import phanes.replay.notice.mapper.NoticeMapper;
import phanes.replay.notice.repository.NoticeImageRepository;
import phanes.replay.notice.repository.NoticeJooqRepository;
import phanes.replay.s3.repository.S3Repository;
import phanes.replay.user.domain.User;
import phanes.replay.user.domain.enums.Role;
import phanes.replay.user.service.UserQueryService;
import phanes.replay.utils.FileUtils;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeQueryService noticeQueryService;
    private final NoticeContentQueryService noticeContentQueryService;
    private final NoticeJooqRepository noticeJooqRepository;
    private final NoticeImageRepository noticeImageRepository;
    private final S3Repository s3Repository;
    private final NoticeMapper noticeMapper;
    private final UserQueryService userQueryService;
    private final S3Properties s3Properties;

    public Page<NoticeRs> findAll(Pageable pageable) {
        Page<Notice> noticeList = noticeQueryService.findAll(pageable);
        List<NoticeRs> contents = noticeList.stream().map(noticeMapper::toNoticeRs).toList();
        return new PageImpl<>(contents, pageable, noticeList.getTotalElements());
    }

    public NoticeDetailRs findByNoticeId(Long id) {
        return noticeMapper.toNoticeDetailRs(noticeJooqRepository.findByIdWithPrevNextNotice(id));
    }

    public String saveTempImage(MultipartFile image) {
        String extension = FileUtils.getExtension(image.getOriginalFilename());
        String fileName = UUID.randomUUID().toString();
        String key = "notice/" + fileName + "." + extension;
        String imageUrl = s3Repository.uploadImage(key, image);
        NoticeImage noticeImage = NoticeImage.builder()
                .s3Key(key)
                .status(Status.PENDING)
                .build();
        noticeImageRepository.save(noticeImage);
        return imageUrl;
    }

    @Transactional
    public void saveNotice(Long userId, NoticeRq noticeRq) {
        User user = userQueryService.findById(userId);
        if (!user.getRole().name().equals(Role.ADMIN.name())) {
            throw new RuntimeException("허용되지 않는 접근입니다.");
        }
        Notice notice = Notice.builder()
                .title(noticeRq.getTitle())
                .build();
        Notice savedNotice = noticeQueryService.save(notice);
        String content = noticeRq.getContent();
        NoticeContent noticeContent = NoticeContent.builder()
                .notice(savedNotice)
                .content(content)
                .build();
        noticeContentQueryService.save(noticeContent);

        Pattern urlPattern = Pattern.compile("(?i)<img\\s+[^>]*?src\\s*=\\s*[\"']([^\"']+)[\"']");
        Matcher urlMatcher = urlPattern.matcher(content);

        while (urlMatcher.find()) {
            String url = urlMatcher.group(1);
            if (url.contains(s3Properties.getUrl())) {
                Pattern keyPattern = Pattern.compile(s3Properties.getUrl() + "/" + s3Properties.getBucket() + "/([^\"']+)");
                Matcher keyMatcher = keyPattern.matcher(url);
                while (keyMatcher.find()) {
                    String key = keyMatcher.group(1);
                    NoticeImage noticeImage = noticeImageRepository.findByS3Key(key);
                    noticeImage.updateStatus();
                    noticeImageRepository.save(noticeImage);
                }
            }
        }
    }

    public void updateNotice(Long userId, Long id, String content) {
        User user = userQueryService.findById(userId);
        if (!user.getRole().name().equals(Role.ADMIN.name())) {
            throw new RuntimeException("허용되지 않는 접근입니다.");
        }
        NoticeContent noticeContent = noticeContentQueryService.findByNoticeId(id);
        noticeContent.updateContent(content);
        noticeContentQueryService.save(noticeContent);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        User user = userQueryService.findById(userId);
        if (!user.getRole().name().equals(Role.ADMIN.name())) {
            throw new RuntimeException("허용되지 않는 접근입니다.");
        }
        Notice notice = noticeQueryService.findById(id);
        NoticeContent noticeContent = noticeContentQueryService.findByNoticeId(id);
        noticeContentQueryService.delete(noticeContent);
        noticeQueryService.delete(notice);
    }
}