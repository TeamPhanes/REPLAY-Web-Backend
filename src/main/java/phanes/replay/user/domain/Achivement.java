package phanes.replay.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "achivement")
public class Achivement {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 100)
    private String description; // 설명

    @Column
    private Integer target; // 목표

}
