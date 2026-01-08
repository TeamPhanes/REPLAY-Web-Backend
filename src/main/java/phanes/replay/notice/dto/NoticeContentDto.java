package phanes.replay.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeContentDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
