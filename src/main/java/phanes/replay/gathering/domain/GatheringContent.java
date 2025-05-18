package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class GatheringContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    @Column
    @ColumnDefault("'모임 소개글입니다'")
    private String content; // 모집 소개

    @Column(length = 8)
    private String price; // 가격

    public static GatheringContent create(
            Gathering gathering,
            String content,
            String price
    ) {
        GatheringContent gathering_content = new GatheringContent();
        gathering_content.gathering = gathering;
        gathering_content.content = content;
        gathering_content.price = price;
        return gathering_content;
    }
}