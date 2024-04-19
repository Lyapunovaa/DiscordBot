package apiClient.dto;

import apiClient.utils.Properties;
import lombok.Builder;
import lombok.Data;

public class DTO_ART {

    @Data
    @Builder
    public static class RequestArtDto {
        @Builder.Default
        private final String model_uri = STR."art://\{Properties.properties.yaCatalogId()}/yandex-art/latest";
        private final MessagesArtDto messagesArtDto;
        private final GenerationOptionsArtDto generationOptionsArtDto;
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

    @Data
    public static class ResponseArtDto {
        private final String id;
        private final String description;
        private final String createdAt;
        private final String createdBy;
        private final String modifiedAt;
        private final boolean done;
        private final String metadata;

    }
}
