package apiClient.api;

import apiClient.dto.DTO_ART;
import apiClient.utils.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

public class RequesterToYaART {
    ObjectMapper objectMapper = new ObjectMapper();

    public byte[] getImageById(String id){

        Request request = new Request.Builder()

                .url(Properties.properties.yandexArtUrl())
                .method("GET",)
                .addHeader("x-folder-id", Properties.properties.yaCatalogId())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", STR."Bearer \{Properties.properties.iamToken()}")
                .build();

    }

    public String generateImage(DTO_ART.RequestArtDto dto) {
        String IAMtoken = Properties.properties.iamToken();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String idOfImage;

        RequestBody body;
        try {
            String json = objectMapper.writeValueAsString(dto);
            body = RequestBody.create(json, MediaType.parse("application/json;"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        Request request = new Request.Builder()
                .url(Properties.properties.yandexArtUrl())
                .method("POST", body)
                .addHeader("x-folder-id", Properties.properties.yaCatalogId())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", STR."Bearer \{IAMtoken}")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            DTO_ART.ResponseArtDto responseBody = objectMapper.readValue(json, DTO_ART.ResponseArtDto.class);
            idOfImage = responseBody.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }



        return idOfImage;
    }

}
