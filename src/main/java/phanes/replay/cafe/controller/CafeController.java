package phanes.replay.cafe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.cafe.dto.response.CafeRs;
import phanes.replay.cafe.mapper.CafeMapper;
import phanes.replay.cafe.service.CafeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class CafeController {

    private final CafeService cafeService;
    private final CafeMapper cafeMapper;

    @GetMapping
    public List<CafeRs> getRandomCafeList(@RequestParam Integer size) {
        return cafeMapper.toCafeRs(cafeService.findRandomCafeList(size));
    }
}