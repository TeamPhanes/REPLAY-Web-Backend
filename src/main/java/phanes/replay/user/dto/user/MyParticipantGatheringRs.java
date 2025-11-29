package phanes.replay.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.gathering.dto.response.Participant;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyParticipantGatheringRs {

    private Long id;
    private List<String> genres;
    private String title;
    private String cafeName;
    private String spotName;
    private String address;
    private Integer playtime;
    private Level level;
    private String image;
    private String name;
    private LocalDateTime date;
    private Integer capacity;
    private List<Participant> participants;
    private Boolean isLiked;
}