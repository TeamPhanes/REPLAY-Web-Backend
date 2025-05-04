package phanes.replay.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.domain.LikeGatheringView;
import phanes.replay.gathering.domain.ParticipatingGatheringView;
import phanes.replay.theme.domain.ParticipatingThemeView;
import phanes.replay.theme.domain.ThemeLikeView;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    UserDTO UserToUserDTO(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    @Mapping(target = "gender", expression = "java(user.getGenderMark() ? user.getGender() : \"\")")
    @Mapping(target = "email", expression = "java(user.getEmailMark() ? user.getEmail() : \"\")")
    OtherUserDTO UserToOtherUserDTO(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);

    @Mapping(source = "name", target = "themeName")
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(participatingThemeView.getGenres().split(\",\")))")
    @Mapping(source = "totalRating", target = "totalRating", qualifiedByName = "truncateTotalRating")
    @Mapping(source = "score", target = "myRating")
    @Mapping(source = "content", target = "reviewComment")
    UserPlayThemeDTO ParticipatingThemeViewToUserPlayThemeDTO(ParticipatingThemeView participatingThemeView);

    @Named("truncateTotalRating")
    default Double truncateTotalRating(BigDecimal totalRating) {
        if (totalRating == null) return null;
        return totalRating.setScale(1, RoundingMode.DOWN).doubleValue();
    }

    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(participatingGatheringView.getGenres().split(\",\")))")
    UserParticipatingGatheringDTO ParticipatingGatheringViewToParticipatingGatheringDTO(ParticipatingGatheringView participatingGatheringView);

    @Mapping(target = "gatheringId", source = "gathering.id")
    @Mapping(target = "gatheringName", source = "gathering.name")
    @Mapping(target = "time", expression = "java(gatheringComment.getCreatedAt().toLocalTime())")
    UserCommentDTO GatheringCommentToUserCommentDTO(GatheringComment gatheringComment);

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(likeGatheringView.getGenres().split(\",\")))")
    UserLikeGatheringDTO LikeGatheringViewToUserLikeGatheringDTO(LikeGatheringView likeGatheringView);

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(themeLikeView.getGenres().split(\",\")))")
    UserLikeThemeDTO ThemeLikeViewToUserLikeThemeDTO(ThemeLikeView themeLikeView);
}
