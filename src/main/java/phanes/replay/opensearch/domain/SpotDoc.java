package phanes.replay.opensearch.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SpotDoc {

    private String name;
    private String state;
    private String city;
    private String address;
//    private Location location;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Location {

        private Double lat;
        private Double lon;
    }
}