package org.liu3ext.ds.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletionTokensDetails {

    @JsonProperty("reasoning_tokens")
    private Integer reasoningTokens;

    // ----- getter / setter -----
    public Integer getReasoningTokens() { return reasoningTokens; }
    public void setReasoningTokens(Integer reasoningTokens) { this.reasoningTokens = reasoningTokens; }
}
