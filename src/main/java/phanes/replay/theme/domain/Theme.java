package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.cafe.domain.Spot;
import phanes.replay.common.domain.CreateTimeEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theme extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Spot spot;
    @Column(length = 20)
    private String title;
    @Column(nullable = false)
    private Integer playtime;
    @Column(length = 10)
    private String level;
    @Column(length = 100)
    private String image;
    @Column(nullable = false)
    private Integer minPlayer;
    @Column(nullable = false)
    private Integer maxPlayer;
    @Column(length = 100)
    private String note;
}