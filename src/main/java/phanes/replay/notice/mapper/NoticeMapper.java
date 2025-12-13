package phanes.replay.notice.mapper;

import org.mapstruct.Mapper;
import phanes.replay.notice.domain.Notice;
import phanes.replay.notice.dto.NoticeRs;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeRs toNoticeRs(Notice notice);
}