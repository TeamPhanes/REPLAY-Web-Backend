package phanes.replay.listener.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.gathering.dto.event.GatheringCreatedEvent;
import phanes.replay.theme.domain.enums.Level;
import phanes.replay.utils.TimeUtils;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDoc {

    private Long id;
    private String name;
    private String date;
    private GatheringTheme theme;
    private GatheringSpot spot;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GatheringTheme {

        private Long id;
        private String title;
        private String image;
        private Integer playtime;
        private Level level;
        private List<String> genres;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GatheringSpot {

        private String address;
    }

    public static GatheringDoc of(GatheringCreatedEvent event) {
        return GatheringDoc.builder()
                .id(event.getId())
                .name(event.getName())
                .date(TimeUtils.toTimeStringWithKST(event.getDate()))
                .theme(GatheringTheme.builder()
                        .id(event.getThemeId())
                        .title(event.getTitle())
                        .image(event.getImage())
                        .playtime(event.getPlaytime())
                        .level(event.getLevel())
                        .genres(event.getGenres())
                        .build())
                .spot(GatheringSpot.builder()
                        .address(event.getAddress())
                        .build())
                .build();
    }
}