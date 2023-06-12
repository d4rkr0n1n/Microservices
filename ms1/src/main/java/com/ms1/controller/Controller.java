package com.ms1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms1.config.Configuration;
import com.ms1.model.Model;
import com.ms1.service.RabbitMQSender;
import com.ms1.service.TopicProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class Controller {
    @Autowired
	RabbitMQSender rabbitMQSender;
    
    private final TopicProducer topicProducer;

    @Autowired
    Configuration configuration;

    @GetMapping("/ms1/{variable}")
    public ResponseEntity<Model> getMs1(@RequestParam("param") String param, @PathVariable String variable,@RequestHeader("header") String header){
        
        Model model = new Model(param,variable,header);
        rabbitMQSender.send(model);
        topicProducer.send("Message kakfa topic");
        log.info(configuration.getValue());
        if(Math.random()<1) {
            return ResponseEntity.ok().body(model);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
