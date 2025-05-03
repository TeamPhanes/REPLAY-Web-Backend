package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import phanes.replay.theme.domain.enums.Level;

@Getter
@Entity
@Immutable
@Table(name = "theme_with_genres")
public class ThemeWithGenreView {

    @Id
    private Long id;
    private String name;
    private String address;
    private Integer playtime;
    @Enumerated(EnumType.STRING)
    private Level level;
    private String cafe;
    private String spot;
    private String state;
    private String city;
    private String image;
}
