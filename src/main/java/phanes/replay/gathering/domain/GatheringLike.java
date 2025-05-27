package phanes.replay.gathering.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringLike {

    @Id
    private Long id;
    @ManyToOne
    private Gathering gathering;
    @ManyToOne
    private User user;
}
