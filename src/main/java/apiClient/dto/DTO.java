package apiClient.dto;

import lombok.Data;

import java.util.List;

public class DTO {

    @Data
    public static class MessageGptDto {
        private String role;
        private String text;
    }

    @Data
    public static class RequestGptDto {
        private String modelUrl;
        private boolean stream;
        private double temperature;
        private int maxTokens;
        private List<MessageGptDto> messages;
    }


    @Data
    public static class ResponseGptDto {
        private ResultGptDto result;
    }


    @Data
    public static class ResultGptDto {
        private List<AlternativesGptDto> alternatives;
        private UsageGptDto usage;
        private String modelVersion;
    }

    @Data
    public static class UsageGptDto {
        private String inputTextTokens;
        private String completionTokens;
        private String totalTokens;
    }

    @Data
    public static class AlternativesGptDto {
        private MessageGptDto message;
        private String status;
    }
}