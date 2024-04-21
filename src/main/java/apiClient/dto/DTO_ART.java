package apiClient.dto;

import apiClient.utils.Properties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class DTO_ART {

    //Request
    @Data
    @Builder
    public static class RequestArtDto {
        @Builder.Default
        private final String model_uri = STR."art://\{Properties.properties.yaCatalogId()}/yandex-art/latest";
        private final List<DTO_ART.MessagesArtDto> messages;
        private final DTO_ART.GenerationOptionsArtDto generation_options;
    }

    @Data
    @Builder
    public static class MessagesArtDto {
        @Builder.Default
        private final int weight = 1;
        private final String text;
    }

    @Data
    @Builder
    public static class GenerationOptionsArtDto {
        @Builder.Default
        private final String mime_type = "image/jpeg";
        private final long seed;
    }


}
