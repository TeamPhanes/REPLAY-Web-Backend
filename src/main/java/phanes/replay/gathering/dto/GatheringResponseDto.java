package phanes.replay.gathering.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import phanes.replay.roomescape.domain.Level;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GatheringResponseDto {
    private String gatheringName;
    private String hostName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registrationStart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registrationEnd;
    private String address;
    private String roomEscapeName;
    private String price;
    private Level level;
    private int playtime;
    private int capacity;
    private String genre;
    private String roomEscapeImage;
}
