package apiClient.dto;

import apiClient.utils.Properties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class DTO_GPT {

    @Data
    @Builder
    public static class MessageGptDto {
        private final String role;
        private final String text;
    }

    @Data
    @Builder
    public static class RequestGptDto {
        @Builder.Default
        private final String modelUri = STR."gpt://\{Properties.properties.yaCatalogId()}/yandexgpt-lite/latest" ;
        @Builder.Default
        private final boolean stream = false;
        @Builder.Default
        private final double temperature = 0.1;
        @Builder.Default
        private final int maxTokens = 2000;
        private final List<MessageGptDto> messages;
    }


    @Data
    @Builder
    public static class ResponseGptDto {
        private final ResultGptDto result;
    }


    @Data
    @Builder
    public static class ResultGptDto {
        private final List<AlternativesGptDto> alternatives;
        private final UsageGptDto usage;
        private final String modelVersion;
    }

    @Data
    @Builder
    public static class UsageGptDto {
        private final String inputTextTokens;
        private final String completionTokens;
        private final String totalTokens;
    }

    @Data
    @Builder
    public static class AlternativesGptDto {
        private final MessageGptDto message;
        private final String status;
    }
}