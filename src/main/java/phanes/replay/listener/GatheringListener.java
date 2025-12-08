package phanes.replay.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import phanes.replay.gathering.dto.event.GatheringCreatedEvent;
import phanes.replay.listener.document.GatheringDoc;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatheringListener {

    private static final String INDEX_PATTERN = "replay-gathering-write";
    private final OpenSearchClient client;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void saveGathering(GatheringCreatedEvent event) {
        IndexRequest<GatheringDoc> request = IndexRequest.of(i -> i.index(INDEX_PATTERN)
                .id(String.valueOf(event.getId()))
                .document(GatheringDoc.of(event)));
        try {
            client.index(request);
        } catch (IOException e) {
            log.error("Failed to index gathering doc. event={}", event, e);
        }
    }
}