package phanes.replay.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.dto.MyCommentDto;
import phanes.replay.gathering.dto.response.Participant;
import phanes.replay.review.dto.ReviewImageDto;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "profileImage", target = "image")
    UserRs toUserRs(User user);

    ProfileRs toProfileRs(User user, Integer createGatheringCount, Integer visitGatheringCount, Integer visitThemeCount, Integer successThemeCount, List<AchievementDto> achievements);

    @Mapping(source = "myCommentDto.createdAt", target = "createdAt")
    MyCommentRs toMyCommentRs(User user, MyCommentDto myCommentDto);

    MyVisitThemeRs toMyVisitThemeRs(MyVisitThemeDto myVisitThemeDto, List<String> genres, List<ReviewImageDto> reviewImages);

    MyParticipantGatheringRs toMyParticipantGatheringRs(MyParticipantGatheringDto myParticipantGatheringDto, List<String> genres, List<Participant> participants);

    MyScheduleRs toMyScheduleRs(MyScheduleDto myScheduleDto, List<String> genres);
}