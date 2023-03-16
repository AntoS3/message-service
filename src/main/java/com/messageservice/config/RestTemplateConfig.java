package com.messageservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestTemplateConfig {

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getPersonById(int id, String jwt) {
        var headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "http://localhost:8083/person/"+id, HttpMethod.GET, entity, String.class).getBody();
    }

}
