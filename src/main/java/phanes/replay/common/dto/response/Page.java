package phanes.replay.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    private Long totalCount;
    private Long currentPage;
    private T data;
}
