package apiClient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

public class DTO_GPT_RESP {


    @Data
    public static class ResponseGptDto {
        private DTO_GPT_RESP.ResultGptDto result;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResultGptDto {
        private List<DTO_GPT_RESP.AlternativesGptDto> alternatives;
        private String modelVersion;
    }


    @Data
    public static class ResponseMessageGptDto {
        private String role;
        private String text;
    }

    @Data
    public static class AlternativesGptDto {
        private DTO_GPT_RESP.ResponseMessageGptDto message;
        private String status;
    }
}
