package phanes.replay.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.gathering.domain.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseTimeEntity {

    private String image;
    private String nickname;
    private String gender;
    private String email;
    private String comment;
    private Boolean genderMark;
    private Boolean emailMark;
    private Long totalGathering;
    private Long totalMakeGathering;
    private Long totalRoomEscape;
    private Long successCount;
    private Long failCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
