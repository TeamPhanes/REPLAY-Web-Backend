package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Theme theme;
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String story;
    @Column(length = 200, nullable = false)
    private String link;
    @Column(length = 100, nullable = false)
    private String image;
}