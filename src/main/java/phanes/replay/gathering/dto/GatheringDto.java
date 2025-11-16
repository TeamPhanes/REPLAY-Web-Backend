package phanes.replay.gathering.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDto {

    private Long id;
    private Long themeId;
    private String name;
    private String image;
    private LocalDateTime date;
    private Integer participantCount;
    private Integer capacity;
    private String cafeName;
    private String spotName;
    private String address;
    private Integer playtime;
    private Level level;
    private Boolean isLiked;
}