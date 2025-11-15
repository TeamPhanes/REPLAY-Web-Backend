package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.cafe.domain.Spot;
import phanes.replay.theme.domain.enums.Level;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Spot spot;
    @Column(length = 20)
    private String title;
    @Column(nullable = false)
    private Integer playtime;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Column(length = 100)
    private String image;
    @Column(nullable = false)
    private Integer minPlayer;
    @Column(nullable = false)
    private Integer maxPlayer;
    @Column(length = 100)
    private String note;
}