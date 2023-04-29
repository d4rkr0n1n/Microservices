package com.ms2.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ms2.model.Model;
@Component
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);  
    @RabbitListener(queues = "test-queue")
	public void recievedMessage(String message) {
        Gson gson = new GsonBuilder().create();
        Model model = gson.fromJson(message, Model.class);
		LOGGER.info("Message: {}",model);
	}
    
}
