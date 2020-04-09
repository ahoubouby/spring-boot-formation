package com.ahoubouby.demo.services;

import com.ahoubouby.demo.configs.ToDoRestClientProperties;
import com.ahoubouby.demo.dmain.Todo;
import execptions.ToDoErrorHandler;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * abdelwahed created on 07/04/2020
 * E-MAI: ahoubouby@gmail.com
 */
@Service
public class ToDoRestClient {

    private RestTemplate restTemplate;
    private ToDoRestClientProperties properties;

    public ToDoRestClient(ToDoRestClientProperties properties) {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new ToDoErrorHandler());
        this.properties = properties;
    }

    public Iterable<Todo> findAll() throws URISyntaxException {

        RequestEntity<Iterable<Todo>> requestEntity = new RequestEntity
                <Iterable<Todo>>(HttpMethod.GET, new URI(properties.getUrl() + properties.getBasePath()));
        ResponseEntity<Iterable<Todo>> response = restTemplate
                .exchange(requestEntity, new ParameterizedTypeReference<Iterable<Todo>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    public Todo findById(String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        return restTemplate
                .getForObject(properties.getUrl() + properties.getBasePath() + "/{id}", Todo.class, params);
    }

    public Todo upsert(Todo toDo) throws URISyntaxException {
        RequestEntity<?> requestEntity = new
                RequestEntity<>(toDo,HttpMethod.POST,new URI(properties.getUrl() +
                properties.getBasePath()));
        ResponseEntity<?> response = restTemplate.exchange(requestEntity,
                new ParameterizedTypeReference<Todo>() {});
        if(response.getStatusCode() == HttpStatus.CREATED){
            return restTemplate.getForObject(Objects.requireNonNull(response.getHeaders().
                    getLocation()),Todo.class);
        }
        return null;
    }
}
