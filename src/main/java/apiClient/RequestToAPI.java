package apiClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RequestToAPI {
    OkHttpClient client = new OkHttpClient();
    String apiUrl = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion";
    Request request = new Request.Builder()
            .url(apiUrl)
            .build();

    public void sendRequestToAPI() {
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
