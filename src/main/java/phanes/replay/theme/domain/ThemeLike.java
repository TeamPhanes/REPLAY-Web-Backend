package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.Getter;
import phanes.replay.user.domain.User;

@Getter
@Entity
public class ThemeLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Theme theme;
    @ManyToOne
    private User user;
}
