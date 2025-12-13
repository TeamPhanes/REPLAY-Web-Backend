package phanes.replay.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.notice.domain.NoticeContent;

import java.util.Optional;

public interface NoticeContentRepository extends JpaRepository<NoticeContent, Long> {

    Optional<NoticeContent> findByNoticeId(Long noticeId);
}