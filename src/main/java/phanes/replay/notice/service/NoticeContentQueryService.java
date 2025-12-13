package phanes.replay.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.notice.domain.NoticeContent;
import phanes.replay.notice.repository.NoticeContentRepository;

@Service
@RequiredArgsConstructor
public class NoticeContentQueryService {

    private final NoticeContentRepository noticeContentRepository;

    public NoticeContent findByNoticeId(Long noticeId) {
        return noticeContentRepository.findByNoticeId(noticeId).orElseThrow();
    }

    public void delete(NoticeContent noticeContent) {
        noticeContentRepository.delete(noticeContent);
    }
}
