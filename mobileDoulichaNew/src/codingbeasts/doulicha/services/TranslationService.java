package codingbeasts.doulicha.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TranslationService {
    private final String apiKey;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TranslationService() {
        this.apiKey = "7fddea0bbd0fecb045c5";
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String translate(String text,String sourceLanguage, String targetLanguage) {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.mymemory.translated.net/get")
                    .newBuilder();
            urlBuilder.addQueryParameter("q", text);
            urlBuilder.addQueryParameter("langpair", sourceLanguage + "|" + targetLanguage);
            urlBuilder.addQueryParameter("key", apiKey);
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder().url(url).build();
            Response response = httpClient.newCall(request).execute();
            String responseBody = response.body().string();
            JsonNode responseJson = objectMapper.readTree(responseBody);
            String translatedText = responseJson.get("responseData").get("translatedText").asText();

            return translatedText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
