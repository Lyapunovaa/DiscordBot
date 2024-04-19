package apiClient.api;

import apiClient.dto.DTO_GPT;
import apiClient.utils.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

public class RequesterToYaGPT {
    ObjectMapper objectMapper = new ObjectMapper();

    public String generateResponse(DTO_GPT.RequestGptDto dto) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body;
        try {
            String json = objectMapper.writeValueAsString(dto);
            body = RequestBody.create(json, MediaType.parse("application/json;"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }


        Request request = new Request.Builder()
                .url(Properties.properties.yandexGptUrl())
                .method("POST", body)
                .addHeader("x-folder-id", Properties.properties.yaCatalogId())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", STR."Bearer \{Properties.properties.iamToken()}")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            DTO_GPT.ResponseGptDto responseBody = objectMapper.readValue(json, DTO_GPT.ResponseGptDto.class);

            return responseBody.getResult().getAlternatives().getFirst().getMessage().getText();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


}
