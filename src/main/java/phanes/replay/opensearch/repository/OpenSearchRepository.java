package phanes.replay.opensearch.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.tools.StringUtils;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.SortOptions;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch._types.query_dsl.MultiMatchQuery;
import org.opensearch.client.opensearch._types.query_dsl.Operator;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.TextQueryType;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Repository;
import phanes.replay.opensearch.domain.GatheringDoc;
import phanes.replay.opensearch.domain.ThemeDoc;
import phanes.replay.opensearch.domain.ThemeSuggestDoc;
import phanes.replay.opensearch.dto.response.Cursor;

import java.io.IOException;
import java.util.ArrayList;
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

    public SearchResponse<ThemeDoc> findAllByLocationAndGenreAndKeyword(Integer size, Cursor cursor, List<String> locations, List<String> genres, String keyword) {
        Query keywordQ = keywordQuery(keyword);
        List<Query> filters = new ArrayList<>();
        Query locQ = locationPairFilter(locations);
        if (locQ != null) {
            filters.add(locQ);
        }
        Query genreQ = genreFilter(genres);
        if (genreQ != null) {
            filters.add(genreQ);
        }
        Query finalQ = new Query.Builder()
                .bool(b -> {
                    b.must(keywordQ);
                    if (!filters.isEmpty()) {
                        b.filter(filters);
                    }
                    return b;
                })
                .build();
        List<SortOptions> sort = StringUtils.isEmpty(keyword)
                ? List.of(SortOptions.of(s -> s.field(f -> f.field("id").order(SortOrder.Asc))))
                : List.of(
                SortOptions.of(s -> s.score(sc -> sc.order(SortOrder.Desc))),
                SortOptions.of(s -> s.field(f -> f.field("id").order(SortOrder.Asc)))
        );

        SearchRequest.Builder builder = new SearchRequest.Builder()
                .index(INDEX_PATTERN)
                .size(size)
                .sort(sort)
                .query(finalQ);
        if (cursor != null) {
            builder.searchAfter(List.of(FieldValue.of(cursor.getScore()), FieldValue.of(cursor.getId())));
        }
        SearchRequest request = builder.build();
        try {
            return client.search(request, ThemeDoc.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to search themes by keyword" + keyword, e);
        }
    }

    private Query locationPairFilter(List<String> locations) {
        if (locations == null || locations.isEmpty()) {
            return null;
        }
        List<Query> should = new ArrayList<>();
        for (String location : locations) {
            if (StringUtils.isEmpty(location)) {
                continue;
            }
            String[] parts = location.trim().split("\\s+");
            if (parts.length == 0) {
                continue;
            }
            FieldValue state = FieldValue.of(parts[0]);
            String city = parts.length == 2 ? parts[1].trim() : null;
            Query one;
            if (!StringUtils.isEmpty(city)) {
                one = new Query.Builder()
                        .bool(b -> b
                                .must(m -> m.term(t -> t.field("spot.state").value(state)))
                                .must(m -> m.term(t -> t.field("spot.city").value(FieldValue.of(city))))
                        )
                        .build();
            } else {
                one = new Query.Builder()
                        .term(t -> t.field("spot.state").value(state))
                        .build();
            }
            should.add(one);
        }
        if (should.isEmpty()) {
            return null;
        }
        return new Query.Builder()
                .bool(b -> b.should(should).minimumShouldMatch("1"))
                .build();
    }

    private Query genreFilter(List<String> genres) {
        if (genres == null || genres.isEmpty()) {
            return null;
        }
        List<FieldValue> values = genres.stream().filter(g -> !StringUtils.isEmpty(g)).map(FieldValue::of).toList();
        if (values.isEmpty()) {
            return null;
        }
        return new Query.Builder()
                .terms(t -> t.field("theme.genres.keyword").terms(v -> v.value(values)))
                .build();
    }

    private Query keywordQuery(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return new Query.Builder().matchAll(m -> m).build();
        }
        Query exactBoost = new Query.Builder()
                .term(t -> t.field("title.keyword").value(FieldValue.of(keyword)).boost(10.0f))
                .build();
        Query multi = new Query.Builder()
                .multiMatch(mm -> mm
                        .query(keyword)
                        .fields("title^3", "title.prefix^2", "title.ngram^1.5")
                        .operator(Operator.And)
                        .type(TextQueryType.MostFields)
                )
                .build();
        return new Query.Builder()
                .bool(b -> b.should(exactBoost).should(multi).minimumShouldMatch("1"))
                .build();
    }
}