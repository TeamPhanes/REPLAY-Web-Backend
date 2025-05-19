package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.*;
import phanes.replay.common.domain.BaseTimeEntity;
import phanes.replay.theme.domain.Theme;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Gathering extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Theme theme;
    private String name;
    private String image;
    private Integer capacity;
    private LocalDateTime dateTime;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
}
