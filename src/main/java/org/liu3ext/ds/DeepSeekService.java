package org.liu3ext.ds;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.liu3ext.ds.vo.DeepSeekResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeepSeekService {
    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    public DeepSeekService(String apiKey) {
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 同步调用 DeepSeek API
     */
    public DeepSeekResponse chat(List<Message> messages) throws IOException {
        DeepSeekRequest request = new DeepSeekRequest(messages);
        String jsonBody = objectMapper.writeValueAsString(request);
        System.out.println(jsonBody);
        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request httpRequest = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            String responseBody = response.body().string();
            System.out.println(responseBody);
            if (!response.isSuccessful()) {
                throw new IOException("response.code=" + response.code() + ""  + responseBody);
            }
            return objectMapper.readValue(responseBody, DeepSeekResponse.class);
        }
    }

    /**
     * 单轮对话（完全 Java 8 兼容）
     */
    public String ask(String userInput) throws IOException {
        // 使用 Arrays.asList 替代 List.of
        List<Message> messages = java.util.Arrays.asList(new Message("user", userInput));
        DeepSeekResponse response = chat(messages);
        return response.getReplyContent();
    }

    /**
     * 带系统提示词
     */
    public String askWithSystem(String systemPrompt, String userInput) throws IOException {
        // 使用 Arrays.asList
        List<Message> messages = java.util.Arrays.asList(
                new Message("system", systemPrompt),
                new Message("user", userInput)
        );
        DeepSeekResponse response = chat(messages);
        return response.getReplyContent();
    }
}
