package phanes.replay.cafe.domain;

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
@Table(name = "spot", indexes = {
        @Index(name = "idx_spot_state_city", columnList = "state,city")
})
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cafe cafe;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 10, nullable = false)
    private String state;
    @Column(length = 10, nullable = false)
    private String city;
    @Column(length = 50, nullable = false)
    private String address;
    @Column(nullable = false)
    private Double lat;
    @Column(nullable = false)
    private Double lng;
    @Column(length = 20)
    private String phone;
}