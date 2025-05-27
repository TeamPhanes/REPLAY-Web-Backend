package phanes.replay.common.dto.mapper;

import org.mapstruct.Mapper;
import phanes.replay.common.dto.response.Page;

@Mapper(componentModel = "spring")
public interface PageMapper<T> {

    Page<T> toPage(Long totalCount, Integer currentPage, T data);
}