package phanes.replay.theme.mapper;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import phanes.replay.theme.domain.Theme;

import static phanes.replay.tables.Theme.THEME;

@Component
public class ThemeRecordMapper {

    public RecordMapper<Record, Theme> themeRecordMapper() {
        return r -> Theme.builder()
                .id(r.get(THEME.ID))
                .title(r.get(THEME.TITLE))
                .playtime(r.get(THEME.PLAYTIME))
                .level(r.get(THEME.LEVEL))
                .image(r.get(THEME.IMAGE))
                .minPlayer(r.get(THEME.MIN_PLAYER))
                .maxPlayer(r.get(THEME.MAX_PLAYER))
                .note(r.get(THEME.NOTE))
                .build();
    }
}