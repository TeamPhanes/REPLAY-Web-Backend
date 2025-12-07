package phanes.replay.gathering.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringCreatedEvent {

    private Long id;
    private String name;
    private LocalDateTime date;
    private Long themeId;
    private String title;
    private String image;
    private Integer playtime;
    private Level level;
    private List<String> genres;
    private String address;
}