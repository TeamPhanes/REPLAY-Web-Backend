package phanes.replay.common.opensearch;

import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.SortOptions;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch._types.query_dsl.MultiMatchQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.TextQueryType;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Repository;
import phanes.replay.common.dto.response.Cursor;
import phanes.replay.common.dto.response.ThemeSuggestDoc;

import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OpenSearchRepository {

    private final OpenSearchClient client;
    private static final String INDEX_PATTERN = "replay-theme-write";

    public SearchResponse<ThemeSuggestDoc> findSuggestByKeyword(Integer size, String keyword, Cursor cursor) {
        Query query = Query.of(q -> q.multiMatch(
                MultiMatchQuery.of(m -> m.query(keyword)
                        .fields("title^5", "title.prefix^2")
                        .type(TextQueryType.BestFields)
                )));
        List<SortOptions> sort = List.of(
                SortOptions.of(s -> s.score(sc -> sc.order(SortOrder.Desc))),
                SortOptions.of(s -> s.field(f -> f.field("id").order(SortOrder.Asc)))
        );
        SearchRequest.Builder builder = new SearchRequest.Builder()
                .index(INDEX_PATTERN)
                .size(size)
                .source(src -> src.filter(f -> f.includes(List.of("id", "title", "spot.name"))))
                .sort(sort)
                .query(query);
        if (cursor != null) {
            builder.searchAfter(List.of(FieldValue.of(cursor.getScore()), FieldValue.of(cursor.getId())));
        }
        SearchRequest request = builder.build();
        try {
            return client.search(request, ThemeSuggestDoc.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to search themes by keyword" + keyword, e);
        }
    }
}