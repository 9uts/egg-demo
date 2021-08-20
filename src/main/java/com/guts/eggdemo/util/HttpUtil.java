package com.guts.eggdemo.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

    private static final String HOST_URL = "http://127.0.0.1:8080";

    public static String stopTicking() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> param = new HashMap<>();
        param.put("username", "Lion");
        restTemplate.getForEntity(HOST_URL + "/stop", String.class, param);
        return "Stop Ticking Succeed";
    }
}
