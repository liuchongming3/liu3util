package org.liu3ext.ds;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeepSeekRequest {

    private String model = "deepseek-v4-flash";  // 或 deepseek-v4-pro[reference:2]
    private String object;
    private List<Message> messages;
    @JsonProperty("max_tokens")
    private int maxTokens = 2048;
    private double temperature = 0.7;
    @JsonProperty("stream")
    private boolean stream = false;

    public DeepSeekRequest() {}
    public DeepSeekRequest(List<Message> messages) {
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }
}
