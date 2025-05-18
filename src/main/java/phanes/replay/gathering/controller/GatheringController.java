package phanes.replay.gathering.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import phanes.replay.gathering.dto.request.CreateGatheringRq;
import phanes.replay.gathering.dto.request.GatheringRq;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.service.GatheringServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringServiceImpl gatheringService;
    @PostMapping
    public ResponseEntity<?> creatGathering(
            @RequestBody @Valid CreateGatheringRq request,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            BindingResult bindingResult
            ){

        System.out.println("컨트롤러 도달");
        try {
            if (bindingResult.hasErrors()) {
                // 어떤 필드에서 에러 났는지 출력
                return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
            }


            Long userId = 1L;


            gatheringService.createGathering(request,userId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "모임 생성 성공!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 메시지 확인 가능

            Map<String, String> response = new HashMap<>();
            response.put("error", "잘못된 요청");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping
    public ResponseEntity<?> getGatheringList(
            @RequestParam(name = "sortBy", required = false) String sortBy
        , @RequestParam(name = "keyword", required = false) String keyword
        , @RequestParam(name = "location", required = false) String location
        , @RequestParam(name = "date", required = false) String date
        , @RequestParam(name = "genre", required = false) String genre
        , @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
        , @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset){

        try{

            GatheringRq request = new GatheringRq();
            request.setSortBy(sortBy);
            request.setKeyword(keyword);
            request.setLocation(location);
            request.setDate(date);
            request.setGenre(genre);
            request.setLimit(limit);
            request.setOffset(offset);

            // 서비스

            List<GatheringRs> response = gatheringService.getGatheringList(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "잘못된 요청");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}