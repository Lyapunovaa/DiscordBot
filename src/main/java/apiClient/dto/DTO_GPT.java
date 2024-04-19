package apiClient.dto;

import apiClient.utils.Properties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
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
        private final String modelUri = STR."gpt://\{Properties.properties.yaCatalogId()}/yandexgpt-lite/latest";
        @Builder.Default
        private final boolean stream = false;
        @Builder.Default
        private final double temperature = 0.1;
        @Builder.Default
        private final int maxTokens = 2000;
        private final List<DTO_GPT.MessageGptDto> messages;
    }


    @Data
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseGptDto {
        private DTO_GPT.ResultGptDto result;
    }


    @Data
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResultGptDto {
        private List<DTO_GPT.AlternativesGptDto> alternatives;
        private DTO_GPT.UsageGptDto usage;
        private String modelVersion;
    }

    @Data
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UsageGptDto {
        private String inputTextTokens;
        private String completionTokens;
        private String totalTokens;

    }

    @Data
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseMessageGptDto {
        private String role;
        private String text;
    }

    @Data
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AlternativesGptDto {
        private DTO_GPT.ResponseMessageGptDto message;
        private String status;
    }

}