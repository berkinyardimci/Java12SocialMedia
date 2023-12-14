package com.socialmedia.config.rabbitmq;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;

    @Value("${rabbitmq.active-status-queue}")
    private String activeStatusQueueName;

    @Value("${rabbitmq.active-status-bindingKey}")
    private String activeStatusBindingKey;

    @Value("${rabbitmq.mail-queue}")
    private String mailQueueName;

    @Value("${rabbitmq.mail-bindingKey}")
    private String mailBindingKey;


    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }
    //Register
    @Bean
    Queue registerQueue(){
        return new Queue(registerQueueName);
    }

    @Bean
    public Binding bindingRegister(Queue registerQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }
    //Activate Status
    @Bean
    Queue activeStatusQueue(){
        return new Queue(activeStatusQueueName);
    }
    @Bean
    public Binding bindingActiveStatus(Queue activeStatusQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(activeStatusQueue).to(exchangeAuth).with(activeStatusBindingKey);
    }

    //Mail
    @Bean
    Queue mailQueue(){
        return new Queue(mailQueueName);
    }
    @Bean
    public Binding bindingMail(Queue mailQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(mailQueue).to(exchangeAuth).with(mailBindingKey);
    }


    /*
    @Bean
    MessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }
     */

}
