package org.liu3ext.ds.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage {

    @JsonProperty("prompt_tokens")
    private Integer promptTokens;

    @JsonProperty("completion_tokens")
    private Integer completionTokens;

    @JsonProperty("total_tokens")
    private Integer totalTokens;

    @JsonProperty("prompt_tokens_details")
    private PromptTokensDetails promptTokensDetails;

    @JsonProperty("completion_tokens_details")
    private CompletionTokensDetails completionTokensDetails;

    @JsonProperty("prompt_cache_hit_tokens")
    private Integer promptCacheHitTokens;

    @JsonProperty("prompt_cache_miss_tokens")
    private Integer promptCacheMissTokens;

    // ----- getter / setter -----
    public Integer getPromptTokens() { return promptTokens; }
    public void setPromptTokens(Integer promptTokens) { this.promptTokens = promptTokens; }

    public Integer getCompletionTokens() { return completionTokens; }
    public void setCompletionTokens(Integer completionTokens) { this.completionTokens = completionTokens; }

    public Integer getTotalTokens() { return totalTokens; }
    public void setTotalTokens(Integer totalTokens) { this.totalTokens = totalTokens; }

    public PromptTokensDetails getPromptTokensDetails() { return promptTokensDetails; }
    public void setPromptTokensDetails(PromptTokensDetails promptTokensDetails) { this.promptTokensDetails = promptTokensDetails; }

    public CompletionTokensDetails getCompletionTokensDetails() { return completionTokensDetails; }
    public void setCompletionTokensDetails(CompletionTokensDetails completionTokensDetails) { this.completionTokensDetails = completionTokensDetails; }

    public Integer getPromptCacheHitTokens() { return promptCacheHitTokens; }
    public void setPromptCacheHitTokens(Integer promptCacheHitTokens) { this.promptCacheHitTokens = promptCacheHitTokens; }

    public Integer getPromptCacheMissTokens() { return promptCacheMissTokens; }
    public void setPromptCacheMissTokens(Integer promptCacheMissTokens) { this.promptCacheMissTokens = promptCacheMissTokens; }
}
