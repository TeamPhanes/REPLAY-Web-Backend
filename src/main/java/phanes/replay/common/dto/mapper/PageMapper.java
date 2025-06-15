package phanes.replay.common.dto.mapper;

import org.springframework.stereotype.Component;
import phanes.replay.common.dto.response.Page;

@Component
public class PageMapper<T> {

    public Page<T> toPage(Long totalCount, Integer currentPage, T data) {
        return Page.<T>builder()
                .totalCount(totalCount)
                .currentPage(Long.valueOf(currentPage))
                .data(data)
                .build();
    }

    public Page<T> toPage(Long totalCount, Long currentPage, T data) {
        return Page.<T>builder()
                .totalCount(totalCount)
                .currentPage(currentPage)
                .data(data)
                .build();
    }
}