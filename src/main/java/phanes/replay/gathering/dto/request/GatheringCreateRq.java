package phanes.replay.gathering.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringCreateRq {

    private String name;
    private String content;
    private String price;
    private Boolean isIndividual;
    private Long themeId;
    private LocalDateTime dateTime;
    @Min(value = 2, message = "모집 인원은 최소 2명 이상이어야 합니다.")
    private Integer capacity;
    @Future(message = "모집 시작 날짜는 현재 이후여야 합니다.")
    private LocalDateTime registrationStart;
    @Future(message = "마감 날짜는 현재 이후여야 합니다.")
    private LocalDateTime registrationEnd;
}