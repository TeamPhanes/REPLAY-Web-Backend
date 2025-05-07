package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Entity
@Getter
@Immutable
@Table(name = "participate_gathering_with_like")
public class GatheringScheduleView {

    @Id
    private Long userId;
    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private Long themeId;
    private String themeName;
    private String listImage;
    private String genres;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer playtime;
    private LocalDateTime dateTime;
    private Integer capacity;
    private Integer participantCount;
    private Boolean isLiked;
}
