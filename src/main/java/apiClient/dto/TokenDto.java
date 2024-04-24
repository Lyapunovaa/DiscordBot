package apiClient.dto;

import apiClient.utils.Properties;
import lombok.Builder;
import lombok.Data;

public class TokenDto {

    @Data
    @Builder
    public static class RequestTokenDto {
        @Builder.Default
       private String yandexPassportOauthToken = Properties.properties.yaGptOauthToken();
    }
}

