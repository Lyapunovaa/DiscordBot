package apiClient.api;

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
                .addHeader("Authorization", STR."Bearer \{Properties.properties.iamToken()}")
                .build();

        try {
            DTO_ART_RESP.GetImageArtDTO responseGetImageArtDTO;
            do {
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                System.out.println("JSONNNN".concat(json.substring(0,100)));
                responseGetImageArtDTO = objectMapper.readValue(json, DTO_ART_RESP.GetImageArtDTO.class);
                if(responseGetImageArtDTO.isDone()){
                    image = Base64.getDecoder().decode(responseGetImageArtDTO.getResponse().getImage());
                    return image;
                }
                Thread.sleep(1000);
            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }

    }

    //Первый шаг. Отправляем запрос с запросом. Получаем идентификатор картинки
    public byte[] sendRequestToGenerateImage(DTO_ART.RequestArtDto dto) {
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

            System.out.println(json);
            DTO_ART_RESP.ResponseGenerateArtDto responseBody = objectMapper.readValue(json, DTO_ART_RESP.ResponseGenerateArtDto.class);
            idOfImage = responseBody.getId();
            System.out.println(idOfImage.concat(" GET ID"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return getImageById(idOfImage);
    }


}
