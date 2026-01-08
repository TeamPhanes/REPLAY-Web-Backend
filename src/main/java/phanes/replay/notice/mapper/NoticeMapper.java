package phanes.replay.notice.mapper;

import org.mapstruct.Mapper;
import phanes.replay.notice.domain.Notice;
import phanes.replay.notice.dto.NoticeDetailDto;
import phanes.replay.notice.dto.response.NoticeDetailRs;
import phanes.replay.notice.dto.response.NoticeRs;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeRs toNoticeRs(Notice notice);

    NoticeDetailRs toNoticeDetailRs(NoticeDetailDto noticeDetailDto);
}