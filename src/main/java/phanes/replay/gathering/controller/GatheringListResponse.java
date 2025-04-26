package phanes.replay.gathering.controller;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import phanes.replay.roomescape.domain.GenresDTO;
import phanes.replay.roomescape.domain.Level;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GatheringListResponse {

    private Long gatheringId; // 모임 아이디

    private String listImage; // 목록 이미지

    private List<GenresDTO> genres = new ArrayList<>(); // 장르

    private Integer playtime; // 플레이 타임

    private String roomEscapeName; // 방탈출테마 이름

    private String name; // 모임 이름

    private String spot; // 지점 이름

    private String dateTime; // 모임 날짜

    private Integer capacity; // 정원

    private Integer participantCount; // 참여 인원

    private String address; // 상세 주소

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NORMAL'")
    private Level level; // 난이도

    private Boolean isLiked; // 찜 여부


}
