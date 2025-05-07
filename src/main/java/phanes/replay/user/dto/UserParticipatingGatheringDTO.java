package phanes.replay.user.dto;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserParticipatingGatheringDTO {
    @JsonIgnore
    private Long userId;
    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private Long themeId;
    private String themeName;
    private String listImage;
    private List<String> genres;
    private String level;
    private Integer playtime;
    private LocalDateTime dateTime;
    private Integer capacity;
    @Setter
    private List<ParticipatingUserDTO> participants;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParticipatingUserDTO {
        private String name;
        private String image;
    }
}
