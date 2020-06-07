package com.fair.service.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fair.util.LoggerUtil;
import com.fair.service.TradeConsumerService;
import com.fair.model.TradeRec;

/**
 * The service of RabbitMQ messaging 
 * It consumes the messages to send to the queues
 */

@Service("rabbitmq")
public class RabbitMQConsumerService implements TradeConsumerService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Value("${fair.rabbitmq.exchange}")
    private String exchange;        

    @Value("${fair.rabbitmq.routingkey}")
    private String routingKey;


        @Override    
        public void sendTrade(TradeRec tradeRec) {
            try {          
                amqpTemplate.convertAndSend(exchange, routingKey, tradeRec);

            } catch (Exception e) {
                LoggerUtil.logError(RabbitMQConsumerService.class, "Error occurred: ", e);
            }
        }

}