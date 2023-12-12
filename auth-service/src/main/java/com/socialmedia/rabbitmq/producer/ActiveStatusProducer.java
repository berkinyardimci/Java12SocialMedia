package com.socialmedia.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveStatusProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String directExchange;

    @Value("${rabbitmq.active-status-bindingKey}")
    private String activeStatusBindingKey;

    public void convertAndSendToRabbit(Long authId){
        rabbitTemplate.convertAndSend(directExchange,activeStatusBindingKey,authId);
    }
}
