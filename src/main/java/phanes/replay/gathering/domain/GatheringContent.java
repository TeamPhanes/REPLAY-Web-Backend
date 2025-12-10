package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.gathering.dto.request.GatheringUpdateRq;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private Boolean isIndividual;

    public void update(GatheringUpdateRq gatheringUpdateRq) {
        this.content = gatheringUpdateRq.getContent();
        this.price = gatheringUpdateRq.getPrice();
        this.isIndividual = gatheringUpdateRq.getIsIndividual();
    }
}