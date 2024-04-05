package apiClient;

import apiClient.configs.ParamsOfGPT;
import apiClient.dto.DTO;
import apiClient.utils.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.util.ArrayList;
import java.util.List;

public  class RequesterToYaGPT {
    ObjectMapper objectMapper = new ObjectMapper();


    public RequestBody buildBody(String textRequest)  {

        // Блок в котором создаем два сообщения и собираем в массив
        DTO.MessageGptDto system = new DTO.MessageGptDto();
        system.setRole("system");
        system.setText("Ты - .");
        DTO.MessageGptDto user = new DTO.MessageGptDto();
        user.setRole("user");
        user.setText(textRequest);

        List<DTO.MessageGptDto> messages = new ArrayList<>();
        messages.add(system);
        messages.add(user);

        DTO.RequestGptDto requestDto = new DTO.RequestGptDto();
        requestDto.setModelUri(STR."gpt://\{Properties.properties.yaCatalogId()}/yandexgpt-lite/latest");
        requestDto.setStream(false);
        requestDto.setTemperature(0.1);
        requestDto.setMaxTokens(2000);
        requestDto.setMessages(messages);

        try {
            String json = objectMapper.writeValueAsString(requestDto);

            RequestBody body = RequestBody.create(MediaType.parse("application/json;"), json);
            return body;
        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }


    public String RequestToAPI(String req)  {
        String IAMtoken = Properties.properties.iamToken();
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = buildBody(req);

        Request request = new Request.Builder()
                .url(Properties.properties.yandexGptUrl())
                .method("POST", body)
                .addHeader("x-folder-id", Properties.properties.yaCatalogId())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", STR."Bearer \{IAMtoken}")
                .build();
        try{
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        DTO.ResponseGptDto responseBody = objectMapper.readValue(json, DTO.ResponseGptDto.class);

      //  System.out.println(responseBody.getResult().getAlternatives().getFirst().getMessage().getText());
    return responseBody.getResult().getAlternatives().getFirst().getMessage().getText();
        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
}
