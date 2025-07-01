package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 100)
    private String address;
    private Integer playtime;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Column(length = 15)
    private String cafe;
    @Column(length = 10)
    private String spot;
    @Column(length = 5)
    private String state;
    @Column(length = 5)
    private String city;
    private String image;
    @OneToMany(mappedBy = "theme")
    List<Genre> genres;
}