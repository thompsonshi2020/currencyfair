package com.fair.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure the RabbitMQ 
 * Also it reads from application.properties file
 */

@Configuration
public class RabbitMQConfig {

    @Value("${fair.rabbitmq.queue}")
    private String queueName;

    @Value("${fair.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${fair.rabbitmq.exchange}")
    private String exchange;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }    

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }
    
    @Bean
    MessageListenerContainer messageListnerContainer(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    @Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
    }
    
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }    
}

