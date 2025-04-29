package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 8)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Theme theme;
}