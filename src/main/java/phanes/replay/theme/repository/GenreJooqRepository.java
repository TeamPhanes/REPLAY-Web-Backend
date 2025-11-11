package phanes.replay.theme.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static phanes.replay.tables.Genre.GENRE;

@Repository
@RequiredArgsConstructor
public class GenreJooqRepository {

    private final DSLContext dsl;

    public Map<Long, List<String>> findAllByThemeIdList(List<Long> themeIdList) {
        return dsl.select(GENRE.THEME_ID, GENRE.NAME)
                .from(GENRE)
                .where(GENRE.THEME_ID.in(themeIdList))
                .fetchGroups(GENRE.THEME_ID, GENRE.NAME);
    }
}