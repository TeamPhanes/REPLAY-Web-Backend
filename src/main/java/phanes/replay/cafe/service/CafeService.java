package phanes.replay.cafe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.cafe.domain.Cafe;
import phanes.replay.cafe.repository.CafeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;

    public List<Cafe> findRandomCafeList(Integer size) {
        Long maxId = cafeRepository.findMaxId();
        Set<Long> randomIds = new HashSet<>();
        while(randomIds.size() == size) {
            Long randomId = ThreadLocalRandom.current().nextLong(1, maxId + 1);
            randomIds.add(randomId);
        }
        return cafeRepository.findAllById(randomIds);
    }
}