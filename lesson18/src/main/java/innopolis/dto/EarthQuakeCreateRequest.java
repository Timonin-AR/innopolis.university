package innopolis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EarthQuakeCreateRequest {

    private List<Features> features;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Features {
        private Properties properties;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private String title;
        private Long time;
        private Double mag;
        private String place;
    }
}
