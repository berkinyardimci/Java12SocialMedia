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

    @Value("${rabbitmq.user-exchange}")
    private String userExchange;

    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;


    @Value("${rabbitmq.register-elastic-queue}")
    private String registerElasticQueueName;

    @Value("${rabbitmq.register-elastic-bindingKey}")
    private String registerElasticBindingKey;

    @Bean
    Queue registerQueue(){
        return new Queue(registerQueueName);
    }
    @Bean
    DirectExchange exchange(){
        return new DirectExchange(userExchange);
    }

    @Bean
    Queue registerElasticQueueName(){
        return new Queue(registerElasticQueueName);
    }

    @Bean
    public Binding bindingElasticRegister(Queue registerElasticQueueName, DirectExchange exchange){
        return BindingBuilder.bind(registerElasticQueueName).to(exchange).with(registerElasticBindingKey);
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
