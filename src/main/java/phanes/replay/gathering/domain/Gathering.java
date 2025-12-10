package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.common.domain.BaseTimeEntity;
import phanes.replay.theme.domain.Theme;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gathering extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Theme theme;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer capacity;
    private LocalDateTime date;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
}