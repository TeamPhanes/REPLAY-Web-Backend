package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Entity
@Getter
@Immutable
@Table(name = "like_gathering_summary")
public class LikeGatheringView {

    @Id
    private Long userId;
    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private Long themeId;
    private String listImage;
    private String themeName;
    private LocalDateTime dateTime;
    private String genres;
    private Integer playtime;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer capacity;
    private Integer participantCount;
    private LocalDateTime registrationEnd;
}
