package org.liu3ext.ds.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PromptTokensDetails {

    @JsonProperty("cached_tokens")
    private Integer cachedTokens;

    // ----- getter / setter -----
    public Integer getCachedTokens() { return cachedTokens; }
    public void setCachedTokens(Integer cachedTokens) { this.cachedTokens = cachedTokens; }
}
