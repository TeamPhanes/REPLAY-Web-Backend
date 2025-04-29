package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ThemeContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Theme theme;
    @Column
    private String story;
    @Column
    private String image; // 상세페이지용 이미지
}
