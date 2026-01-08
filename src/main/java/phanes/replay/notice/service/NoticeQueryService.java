package phanes.replay.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.notice.domain.Notice;
import phanes.replay.notice.repository.NoticeRepository;

@Service
@RequiredArgsConstructor
public class NoticeQueryService {

    private final NoticeRepository noticeRepository;

    public Page<Notice> findAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElseThrow();
    }

    public void delete(Notice notice) {
        noticeRepository.delete(notice);
    }

    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }
}