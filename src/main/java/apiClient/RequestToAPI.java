package apiClient;

import apiClient.dto.DTO;
import apiClient.utils.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestToAPI {
    ObjectMapper objectMapper = new ObjectMapper();


    public RequestBody buildBody() throws Exception {

        // Блок в котором создаем два сообщения и собираем в массив
        DTO.MessageGptDto system = new DTO.MessageGptDto();
        system.setRole("system");
        system.setText("Ты заменяешь первое слово в своём ответе на Шалом");
        DTO.MessageGptDto user = new DTO.MessageGptDto();
        user.setRole("user");
        user.setText("Подскажи какое расстоянее способен пролтеть аист");

        List<DTO.MessageGptDto> messages = new ArrayList<>();
        messages.add(system);
        messages.add(user);

        DTO.RequestGptDto requestDto = new DTO.RequestGptDto();
        requestDto.setModelUri(STR."gpt://\{Properties.properties.yaCatalogId()}/yandexgpt-lite/latest");
        requestDto.setStream(false);
        requestDto.setTemperature(0.1);
        requestDto.setMaxTokens(2000);
        requestDto.setMessages(messages);

        String json = objectMapper.writeValueAsString(requestDto);

        RequestBody body = RequestBody.create(MediaType.parse("application/json;"), json);
        return body;
    }


    public void RequestToAPI() throws Exception {
        String IAMtoken = Properties.properties.iamToken();
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = buildBody();

        Request request = new Request.Builder()
                .url(Properties.properties.yandexGptUrl())
                .method("POST", body)
                .addHeader("x-folder-id", Properties.properties.yaCatalogId())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", STR."Bearer \{IAMtoken}")
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        DTO.ResponseGptDto responseBody = objectMapper.readValue(json, DTO.ResponseGptDto.class);

        System.out.println(responseBody.getResult().getAlternatives().getFirst().getMessage().getText());
    }
}
