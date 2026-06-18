package org.liu3ext.ds.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice {

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("logprobs")
    private Object logprobs;   // 通常为 null，用 Object 兼容

    @JsonProperty("finish_reason")
    private String finishReason;

    // ----- getter / setter -----
    public Integer getIndex() { return index; }
    public void setIndex(Integer index) { this.index = index; }

    public Message getMessage() { return message; }
    public void setMessage(Message message) { this.message = message; }

    public Object getLogprobs() { return logprobs; }
    public void setLogprobs(Object logprobs) { this.logprobs = logprobs; }

    public String getFinishReason() { return finishReason; }
    public void setFinishReason(String finishReason) { this.finishReason = finishReason; }
}
