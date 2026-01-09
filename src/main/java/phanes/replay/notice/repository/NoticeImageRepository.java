package phanes.replay.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.notice.domain.NoticeImage;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {

    NoticeImage findByS3Key(String key);
}