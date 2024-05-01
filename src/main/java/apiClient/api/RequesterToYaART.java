package apiClient.api;

import apiClient.configs.TokenManager;
import apiClient.dto.DTO_ART;
import apiClient.dto.DTO_ART_RESP;
import apiClient.utils.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.util.Base64;

public class RequesterToYaART {
    static ObjectMapper objectMapper = new ObjectMapper();

    //Отправляем идентификатор, получаем массив байт
    public static byte[] getImageById(String id) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        byte[] image;

        Request request = new Request.Builder()
                .url(Properties.properties.yandexGetArtUrl().concat(id))
                .get()
                .addHeader("x-folder-id", Properties.properties.yaCatalogId())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer ".concat(TokenManager.getInstance().getIamtoken()))
                .build();

        try {
            DTO_ART_RESP.GetImageArtDTO responseGetImageArtDTO;
            do {
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                responseGetImageArtDTO = objectMapper.readValue(json, DTO_ART_RESP.GetImageArtDTO.class);
                if (responseGetImageArtDTO.isDone()) {
                    image = Base64.getDecoder().decode(responseGetImageArtDTO.getResponse().getImage());
                    return image;
                }
                //"Поллинг"
                Thread.sleep(1000);
            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }

    }

    //Первый шаг. Отправляем запрос с запросом. Получаем идентификатор картинки
    public byte[] sendRequestToGenerateImage(DTO_ART.RequestArtDto dto) {
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
                .addHeader("Authorization", "Bearer ".concat(TokenManager.getInstance().getIamtoken()))
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            DTO_ART_RESP.ResponseGenerateArtDto responseBody = objectMapper.readValue(json, DTO_ART_RESP.ResponseGenerateArtDto.class);

            if (!response.isSuccessful() && !responseBody.getError().isEmpty()) {
                throw new Exception(responseBody.getError());
            }
            idOfImage = responseBody.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return getImageById(idOfImage);
    }


}
