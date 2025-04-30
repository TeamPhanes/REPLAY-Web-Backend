package phanes.replay.user.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.UserDTO;

import java.util.List;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-29T16:36:34+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO UserToUserDTO(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount , List<String> representAchievement) {
        if ( user == null && totalGathering == null && totalMakeGathering == null && totalTheme == null && successCount == null && failCount == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        if ( user != null ) {
            userDTO.comment( user.getProfileComment() );
            userDTO.image( user.getProfileImage() );
            userDTO.nickname( user.getNickname() );
            userDTO.gender( user.getGender() );
            userDTO.email( user.getEmail() );
            userDTO.genderMark( user.getGenderMark() );
            userDTO.emailMark( user.getEmailMark() );
        }
        userDTO.totalGathering( totalGathering );
        userDTO.totalMakeGathering( totalMakeGathering );
        userDTO.totalTheme( totalTheme );
        userDTO.successCount( successCount );
        userDTO.failCount( failCount );

        return userDTO.build();
    }
}
