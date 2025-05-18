package phanes.replay.user.dto.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.query.*;
import phanes.replay.user.dto.user.response.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    UserRs toUserRs(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    @Mapping(target = "gender", expression = "java(user.getGenderMark() ? user.getGender() : \"\")")
    @Mapping(target = "email", expression = "java(user.getEmailMark() ? user.getEmail() : \"\")")
    OtherUserRs toOtherUserRs(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);

    @Mapping(source = "name", target = "themeName")
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(userVisitThemeQuery.getGenres().split(\",\")))")
    @Mapping(source = "score", target = "myRating")
    @Mapping(source = "content", target = "reviewComment")
    UserVisitThemeRs toUserVisitThemeRs(UserVisitThemeQuery userVisitThemeQuery);

    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(userParticipantGatheringQuery.getGenres().split(\",\")))")
    UserParticipatingGatheringRs toUserParticipatingGatheringRs(UserParticipantGatheringQuery userParticipantGatheringQuery);

    @Mapping(target = "gatheringId", source = "gathering.id")
    @Mapping(target = "gatheringName", source = "gathering.name")
    UserCommentRs toUserCommentRs(GatheringComment gatheringComment);

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(userLikeGatheringQuery.getGenres().split(\",\")))")
    UserLikeGatheringRs toUserLikeGatheringRs(UserLikeGatheringQuery userLikeGatheringQuery);

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(userLikeThemeQuery.getGenres().split(\",\")))")
    UserLikeThemeRs toUserLikeThemeRs(UserLikeThemeQuery userLikeThemeQuery);


    @Mapping(target = "time", expression = "java(userSchedule.getDateTime().toLocalTime())")
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(userSchedule.getGenres().split(\",\")))")
    UserScheduleRs toUserScheduleRs(UserSchedule userSchedule);
}
