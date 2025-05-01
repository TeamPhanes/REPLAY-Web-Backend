package phanes.replay.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.theme.domain.ParticipatingThemeView;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.dto.UserPlayThemeDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    UserDTO UserToUserDTO(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);

    @Mapping(source = "name", target = "themeName")
    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(participatingThemeView.getGenres().split(\",\")))")
    @Mapping(source = "totalRating", target = "totalRating")
    @Mapping(source = "score", target = "myRating")
    @Mapping(source = "content", target = "reviewComment")
    UserPlayThemeDTO ParticipatingThemeViewToUserPlayThemeDTO(ParticipatingThemeView participatingThemeView);
}
