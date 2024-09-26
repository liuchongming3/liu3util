package org.liu3.util.download;

import org.springframework.web.client.RestTemplate;

/**
 * @Author: liutianshuo
 * @Date: 2021/7/15
 */
public class RestTemplateDl {

    RestTemplate restTemplate = null;

    public RestTemplateDl() {
        restTemplate = new RestTemplate();
    }
}
