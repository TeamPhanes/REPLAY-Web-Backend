package phanes.replay.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}