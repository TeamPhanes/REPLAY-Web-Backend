package phanes.replay.notice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import phanes.replay.notice.dto.NoticeContentDto;
import phanes.replay.notice.dto.NoticeDetailDto;
import phanes.replay.notice.dto.response.NoticeRs;

import static phanes.replay.tables.Notice.NOTICE;
import static phanes.replay.tables.NoticeContent.NOTICE_CONTENT;

@Repository
@RequiredArgsConstructor
public class NoticeJooqRepository {

    private final DSLContext dsl;

    public NoticeDetailDto findByIdWithPrevNextNotice(Long id) {
        NoticeRs prev = dsl.select(NOTICE.ID, NOTICE.TITLE, NOTICE.CREATED_AT)
                .from(NOTICE)
                .where(NOTICE.ID.lt(id))
                .orderBy(NOTICE.ID.desc())
                .limit(1)
                .fetchOneInto(NoticeRs.class);
        NoticeContentDto current = dsl.select(NOTICE.ID, NOTICE.TITLE, NOTICE.CREATED_AT, NOTICE_CONTENT.CONTENT)
                .from(NOTICE)
                .join(NOTICE_CONTENT).on(NOTICE.ID.eq(NOTICE_CONTENT.NOTICE_ID))
                .where(NOTICE.ID.eq(id))
                .fetchOneInto(NoticeContentDto.class);
        NoticeRs next = dsl.select(NOTICE.ID, NOTICE.TITLE, NOTICE.CREATED_AT)
                .from(NOTICE)
                .where(NOTICE.ID.gt(id))
                .orderBy(NOTICE.ID.asc())
                .limit(1)
                .fetchOneInto(NoticeRs.class);
        return NoticeDetailDto.builder()
                .prev(prev)
                .next(next)
                .current(current)
                .build();
    }
}