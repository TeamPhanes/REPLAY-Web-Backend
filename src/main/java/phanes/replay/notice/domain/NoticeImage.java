package phanes.replay.notice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.notice.domain.enums.Status;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String s3Key;
    @Enumerated(EnumType.STRING)
    private Status status;

    public void updateStatus() {
        this.status = Status.SAVE;
    }
}