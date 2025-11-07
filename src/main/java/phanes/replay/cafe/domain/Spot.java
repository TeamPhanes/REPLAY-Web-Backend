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
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cafe cafe;
    @Column(length = 20)
    private String name;
    @Column(length = 10)
    private String state;
    @Column(length = 10)
    private String city;
    @Column(length = 50)
    private String address;
    private Double lat;
    private Double lng;
    @Column(length = 20)
    private String phone;
}