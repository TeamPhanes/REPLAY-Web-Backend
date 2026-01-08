package phanes.replay.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.notice.dto.response.NoticeRs;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailDto {

    private NoticeRs prev;
    private NoticeRs next;
    private NoticeContentDto current;
}