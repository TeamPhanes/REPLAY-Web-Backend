package phanes.replay.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.review.domain.enums.Eval;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    private Eval label;
    private Double percent;

    public static Evaluation emptyValue() {
        return Evaluation.builder()
                .label(Eval.LIKE)
                .percent(0.0)
                .build();
    }
}
