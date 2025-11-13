package org.example.logistica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixResponse {

    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;

    @JsonProperty("origin_addresses")
    private List<String> originAddresses;

    private List<Row> rows;

    private String status;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Row {
        private List<Element> elements;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Element {
        private Distance distance;
        private Duration duration;
        private String status;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Distance {
        private String text;  // "150 km"
        private Long value;   // 150000 (en metros)
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Duration {
        private String text;  // "2 hours 30 mins"
        private Long value;   // 9000 (en segundos)
    }
}