package phanes.replay.user.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import phanes.replay.user.dto.user.AchievementDto;

import java.util.List;

import static phanes.replay.tables.Achievement.ACHIEVEMENT;
import static phanes.replay.tables.AchievementProgress.ACHIEVEMENT_PROGRESS;

@Repository
@RequiredArgsConstructor
public class AchievementJooqRepository {

    private final DSLContext dsl;

    public List<AchievementDto> findByUserIdAndOwner(Long userId, boolean isOwner) {
        Condition where = DSL.trueCondition();
        if (!isOwner) {
            where.and(ACHIEVEMENT_PROGRESS.IS_REPRESENTATIVE.eq(true));
        }
        where = where.and(ACHIEVEMENT_PROGRESS.USER_ID.eq(userId));
        return dsl.select(ACHIEVEMENT.ID, ACHIEVEMENT_PROGRESS.PROGRESS, ACHIEVEMENT_PROGRESS.COMPLETED_AT, ACHIEVEMENT_PROGRESS.IS_REPRESENTATIVE)
                .from(ACHIEVEMENT)
                .join(ACHIEVEMENT_PROGRESS).on(ACHIEVEMENT.ID.eq(ACHIEVEMENT_PROGRESS.ACHIEVEMENT_ID))
                .where(where)
                .fetchInto(AchievementDto.class);
    }
}