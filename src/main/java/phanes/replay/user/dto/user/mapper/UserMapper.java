package phanes.replay.user.dto.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.domain.GatheringScheduleView;
import phanes.replay.theme.domain.ThemeLikeView;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.query.UserLikeGatheringQuery;
import phanes.replay.user.dto.user.query.UserParticipantGatheringQuery;
import phanes.replay.user.dto.user.query.UserVisitThemeQuery;
import phanes.replay.user.dto.user.response.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    UserRs UserToUserDTO(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    @Mapping(target = "gender", expression = "java(user.getGenderMark() ? user.getGender() : \"\")")
    @Mapping(target = "email", expression = "java(user.getEmailMark() ? user.getEmail() : \"\")")
    OtherUserRs UserToOtherUserDTO(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);

    @Mapping(source = "name", target = "themeName")
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(participatingThemeView.getGenres().split(\",\")))")
    @Mapping(source = "score", target = "myRating")
    @Mapping(source = "content", target = "reviewComment")
    UserVisitThemeRs toUserVisitThemeRs(UserVisitThemeQuery userVisitThemeQuery);

    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(participatingGatheringView.getGenres().split(\",\")))")
    UserParticipatingGatheringRs ParticipatingGatheringViewToParticipatingGatheringDTO(UserParticipantGatheringQuery userParticipantGatheringQuery);

    @Mapping(target = "gatheringId", source = "gathering.id")
    @Mapping(target = "gatheringName", source = "gathering.name")
    UserCommentRs GatheringCommentToUserCommentDTO(GatheringComment gatheringComment);

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(likeGatheringView.getGenres().split(\",\")))")
    UserLikeGatheringRs LikeGatheringViewToUserLikeGatheringDTO(UserLikeGatheringQuery userLikeGatheringQuery);

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(themeLikeView.getGenres().split(\",\")))")
    UserLikeThemeRs ThemeLikeViewToUserLikeThemeDTO(ThemeLikeView themeLikeView);


    @Mapping(target = "time", expression = "java(gatheringScheduleView.getDateTime().toLocalTime())")
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(gatheringScheduleView.getGenres().split(\",\")))")
    UserScheduleDTO GatheringScheduleViewToUserScheduleDTO(GatheringScheduleView gatheringScheduleView);
}
