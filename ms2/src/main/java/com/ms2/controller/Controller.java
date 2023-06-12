package com.ms2.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class Controller {

  private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

  @GetMapping("/ms2")
  @CircuitBreaker(name = "myProjectAllRemoteCallsCB", fallbackMethod = "getAPIFallBack")
  public ResponseEntity<String> getMs1() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    headers.add("header", "hvalue");
    HttpEntity<?> entity = new HttpEntity<>(headers);

    String urlTemplate = UriComponentsBuilder.fromHttpUrl("http://api-gateway/api/v1/ms1/{variable}")
        .queryParam("param", "pvalue")
        .encode()
        .toUriString();

    Map<String, String> params = new HashMap<>();
    params.put("variable", "vvalue");
    HttpEntity<String> response;
    try{
      RestOperations operations = new RestTemplate();
  
      response = operations.exchange(
          urlTemplate,
          HttpMethod.GET,
          entity,
          String.class,
          params);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new HttpServerErrorException(HttpStatusCode.valueOf(500));
    }

    LOGGER.info("");
    return ResponseEntity.ok().body(response.getBody());
  }

  public String getAPIFallBack(String topicPage, Exception e) {
    log.error("getAPIFallBack::{}", e);
    log.info(topicPage);
    return "";
  }
}
