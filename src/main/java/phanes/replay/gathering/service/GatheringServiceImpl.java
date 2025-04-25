package phanes.replay.gathering.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phanes.replay.gathering.controller.GatheringListRequest;
import phanes.replay.gathering.controller.GatheringListResponse;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.Gathering_Content;
import phanes.replay.gathering.domain.Gathering_Member;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.gathering.controller.GatheringCreateRequest;
import phanes.replay.gathering.repository.Gathering_ContentRepository;
import phanes.replay.gathering.repository.Gathering_MemberRepository;
import phanes.replay.roomescape.domain.GenresDTO;
import phanes.replay.roomescape.domain.RoomEscape;
import phanes.replay.roomescape.repository.RoomEscapeRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GatheringServiceImpl implements GatheringService {

    private final GatheringRepository gatheringRepository;
    private final Gathering_ContentRepository gathering_contentRepository;
    private final Gathering_MemberRepository gathering_memberRepository;
    private final UserRepository userRepository;
    private final RoomEscapeRepository roomEscapeRepository;
    private final GatheringMapper gatheringMapper;

    @Transactional
    @Override
    public void createGathering(GatheringCreateRequest request, Long userId) {
        // 엔티티 조회
        User user = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User Not Found"));
        if(user == null) {
            throw new RuntimeException("유저 없음");
        }
        RoomEscape roomEscape = roomEscapeRepository.findById(request.getRoomescapeId()).orElseThrow(() -> new IllegalArgumentException("room escape not found"));

        // 엔티티 생성
        Gathering gathering = gatheringMapper.requestToEntity(request, roomEscape);
        Gathering savedGathering = gatheringRepository.save(gathering);

        // 콘텐츠 생성
        Gathering_Content content = gatheringMapper.ToContentEntity(request, savedGathering);
        gathering_contentRepository.save(content);

        // 멤버 추가
        Gathering_Member member = Gathering_Member.createHost(user, savedGathering);
        gathering_memberRepository.save(member);
    }

    @Override
    public List<GatheringListResponse> getGatheringList(GatheringListRequest request) {
        Sort sort;
        if ("registrationEnd".equals(request.getSortBy())) {
            sort = Sort.by(Sort.Direction.ASC, "registrationEnd");
        }else if ("participantCount".equals(request.getSortBy())) {
                sort = Sort.by(Sort.Direction.DESC, "participantCount");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "dateTime");
        }

        // 페이지 설정
        Pageable pageable = PageRequest.of(
                request.getOffset() != null ? request.getOffset() : 0,
                request.getLimit() != null ? request.getLimit() : 10,
                sort
        );

        Page<Gathering> gatherings;

        // 단일 조건 검색
        if (onlyOneParameterProvided(request)){
            if(request.getKeyword() != null && !request.getKeyword().isEmpty()){
                // 키워드 검색
                List<Gathering> results = gatheringRepository.findByNameContainingOrRoomEscapeNameContaining(
                        request.getKeyword(), request.getKeyword(), pageable);
                return convertToResponseList(results);
            } else if (request.getLocation() != null && !request.getLocation().isEmpty()) {
                // 지역 검색
                List<Gathering> results = gatheringRepository.findByLocation(
                        request.getLocation(), pageable);
                return convertToResponseList(results);
            } else if (request.getDate() != null && !request.getDate().isEmpty()) {
                // 날짜 검색
                LocalDate date = LocalDate.parse(request.getDate());
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(23, 59, 59);
                List<Gathering> results = gatheringRepository.findByDateTimeBetween(startOfDay, endOfDay, pageable);
                return convertToResponseList(results);
            } else if (request.getGenre() != null && !request.getGenre().isEmpty()) {
                // 장르 검색
                List<Gathering> results = gatheringRepository.findByGenre(request.getGenre(), pageable);
                return convertToResponseList(results);
            }
        }
        // 2. 여러 조건을 조합하여 검색하는 경우 (복합 쿼리 사용)
        LocalDateTime dateParam = null;
        if (request.getDate() != null && !request.getDate().isEmpty()) {
            dateParam = LocalDate.parse(request.getDate()).atStartOfDay();
        }

        gatherings = gatheringRepository.findGatheringsWithFilters(
                request.getKeyword(),
                request.getLocation(),
                dateParam,
                request.getGenre(),
                pageable
        );

        return convertToResponseList(gatherings.getContent());
    }

    // 단일 파라미터만 제공되었는지 확인하는 메서드
    private boolean onlyOneParameterProvided(GatheringListRequest request) {
        int paramCount = 0;
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) paramCount++;
        if (request.getLocation() != null && !request.getLocation().isEmpty()) paramCount++;
        if (request.getDate() != null && !request.getDate().isEmpty()) paramCount++;
        if (request.getGenre() != null && !request.getGenre().isEmpty()) paramCount++;
        return paramCount == 1;
    }
    // 엔티티 리스트를 응답 DTO 리스트로 변환하는 메서드
    private List<GatheringListResponse> convertToResponseList(List<Gathering> gatherings) {
        return gatherings.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    // 개별 엔티티를 응답 DTO로 변환하는 메서드
    private GatheringListResponse convertToResponse(Gathering gathering) {
        GatheringListResponse response = new GatheringListResponse();

        response.setGatheringId(gathering.getId());
        response.setName(gathering.getName());

        // RoomEscape 정보 설정
        RoomEscape roomEscape = gathering.getRoomEscape();
        if (roomEscape != null) {
            response.setRoomEscapeName(roomEscape.getName());
            response.setAddress(roomEscape.getAddress());
            response.setSpot(roomEscape.getSpot());

            if (roomEscape.getLevel() != null) {
                response.setLevel(roomEscape.getLevel());
            }

            response.setPlaytime(roomEscape.getPlaytime());

            // 장르 정보 설정
            List<GenresDTO> genres = roomEscape.getGenres().stream()
                    .map(genre -> new GenresDTO(genre.getName()))
                    .collect(Collectors.toList());
            response.setGenres(genres);
        }

        response.setListImage(gathering.getListImage());
        response.setDateTime(gathering.getDateTime().toString());
        response.setCapacity(gathering.getCapacity());

        // 참가자 수 계산
        int participantCount = gathering.getGathering_member() != null ? gathering.getGathering_member().size() : 0;
        response.setParticipantCount(participantCount);

        // 찜 정보는 인증이 없으므로 제외
        response.setIsLiked(false);

        return response;
    }
}
