package apiClient;

import apiClient.utils.Properties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class RequestToAPI {


    public void RequestToAPI() throws IOException {
        String IAMtoken = "";

        OkHttpClient client = new OkHttpClient().newBuilder().build();


        RequestBody body = RequestBody.create(STR."""
                {"modelUri":"gpt://\{Properties.properties.yaCatalogId()}/yandexgpt/latest","completionOptions":{"stream":false,"temperature":0.1,"maxTokens":"2000"},"messages":[{"role":"system","text":"Ты робот который помогает всем решить их проблемы"},{"role":"user","text":"Подскажи какой язык программирования выбрать для изучения в качестве первого языка и почему?"}]}
                """.getBytes());


        Request request = new Request.Builder().url(Properties.properties.yandexGptUrl()).method("POST", body).addHeader("x-folder-id", Properties.properties.yaCatalogId()).addHeader("Content-Type", "application/json").addHeader("Authorization", STR."Bearer \{IAMtoken}").build();
        System.out.println((request.body()));
        Response response = client.newCall(request).execute();
        System.out.println(STR."Response BODY \n\{response.body().string()}");
    }
}
