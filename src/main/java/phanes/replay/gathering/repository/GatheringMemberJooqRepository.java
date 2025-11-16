package phanes.replay.gathering.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.dto.response.Participant;

import java.util.List;

import static phanes.replay.tables.GatheringMember.GATHERING_MEMBER;
import static phanes.replay.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class GatheringMemberJooqRepository {

    private final DSLContext dsl;

    public List<Participant> findAllByGatheringId(Long gatheringId) {
        return dsl.select(USERS.ID, USERS.NICKNAME, USERS.PROFILE_IMAGE, USERS.EMAIL)
                .from(GATHERING_MEMBER)
                .join(USERS).on(GATHERING_MEMBER.USER_ID.eq(USERS.ID))
                .where(GATHERING_MEMBER.GATHERING_ID.eq(gatheringId))
                .fetchInto(Participant.class);
    }
}