package com.ms1.service;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ms1.model.Model;


    @Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("Direct-Exchange")
	private String exchange;
	
	@Value("routing-key")
	private String routingkey;	
	
	public void send(Model model) {
		// rabbitTemplate.convertAndSend(exchange, routingkey, model);
		amqpTemplate.convertAndSend(exchange, routingkey, model);
		System.out.println("Send msg = " + model);
	    
	}
}
