package phanes.replay.gathering.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.response.Participant;

import java.util.List;

import static phanes.replay.tables.GatheringMember.GATHERING_MEMBER;
import static phanes.replay.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class GatheringMemberJooqRepository {

    private final DSLContext dsl;

    public List<Participant> findAllByGatheringId(Long gatheringId) {
        return dsl.select(USERS.ID, USERS.NICKNAME, USERS.PROFILE_IMAGE, DSL.when(USERS.EMAIL_MARK.eq(true), USERS.EMAIL).otherwise("").as("email"))
                .select(GATHERING_MEMBER.ROLE)
                .from(GATHERING_MEMBER)
                .join(USERS).on(GATHERING_MEMBER.USER_ID.eq(USERS.ID))
                .where(GATHERING_MEMBER.GATHERING_ID.eq(gatheringId))
                .fetchInto(Participant.class);
    }

    public List<Role> findParticipantAllByUserId(Long userId) {
        return dsl.select(GATHERING_MEMBER.ROLE)
                .from(GATHERING_MEMBER)
                .where(GATHERING_MEMBER.USER_ID.eq(userId))
                .fetchInto(Role.class);
    }
}