package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GatheringContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;
    private String content;
    private String price;
    private String image;
}