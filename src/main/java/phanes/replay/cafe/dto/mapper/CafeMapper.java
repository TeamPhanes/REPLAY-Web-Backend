package phanes.replay.cafe.dto.mapper;

import org.mapstruct.Mapper;
import phanes.replay.cafe.domain.Cafe;
import phanes.replay.cafe.dto.response.CafeRs;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CafeMapper {

    List<CafeRs> toCafeRs(List<Cafe> cafe);
}
