package phanes.replay.notice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.notice.dto.NoticeContentDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailRs {

    private NoticeRs prev;
    private NoticeRs next;
    private NoticeContentRs current;
}