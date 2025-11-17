package phanes.replay.gathering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDetailRs {

    private Long id;
    private String name;
    private Integer capacity;
    private LocalDateTime date;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
    private String content;
    private String image;
    private Long price;
    private Boolean isIndividual;
    private Integer participantCount;
    private List<Participant> participants;
    private Long themeId;
    private String title;
    private String address;
    private List<String> genres;
    private Boolean isLiked;
}