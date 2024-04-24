package apiClient.dto;

import lombok.Data;

public class TokenGetDto {

    @Data
    public static class ResponseTokenDto {
        private String iamToken;
        private String expiresAt;
    }
}
