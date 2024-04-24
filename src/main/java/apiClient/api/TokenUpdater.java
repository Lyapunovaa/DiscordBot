package apiClient.api;

import apiClient.configs.TokenManager;
import apiClient.dto.TokenDto;
import apiClient.dto.TokenGetDto;
import apiClient.utils.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

public class TokenUpdater {
    static ObjectMapper objectMapper = new ObjectMapper();


    public void updateToken() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        TokenDto.RequestTokenDto tokenDto = TokenDto.RequestTokenDto.builder().build();


        RequestBody body;
        try {
            String json = objectMapper.writeValueAsString(tokenDto);
            body = RequestBody.create(json, MediaType.parse("application/json;"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        Request request = new Request.Builder()
                .url(Properties.properties.yandexUpdateTokenUrl())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {

            Response response = client.newCall(request).execute();
            String json = response.body().string();
            TokenGetDto.ResponseTokenDto responseBody = objectMapper.readValue(json, TokenGetDto.ResponseTokenDto.class);
            TokenManager.getInstance().setIamtoken(responseBody.getIamToken());

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


}
