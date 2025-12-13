package phanes.replay.notice.mapper;

import org.mapstruct.Mapper;
import phanes.replay.notice.domain.Notice;
import phanes.replay.notice.domain.NoticeContent;
import phanes.replay.notice.dto.response.NoticeContentRs;
import phanes.replay.notice.dto.response.NoticeRs;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeRs toNoticeRs(Notice notice);

    NoticeContentRs toNoticeContentRs(NoticeContent noticeContent);
}