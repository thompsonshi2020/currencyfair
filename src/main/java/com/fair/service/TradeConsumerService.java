package com.fair.service;

import org.springframework.scheduling.annotation.Async;
import com.fair.model.TradeRec;


/**
 * TradeConsumerService interface is a Strategy Pattern.
 * For any brand of MQ service, such as ActiveMQ, Hazelcast, Kafka, RabbitMQ, ZeroMQ, etc,  
 * you can implement this interface to consume the submitted trade messages 
 * from exposed endpoint.
 */

public interface TradeConsumerService {
    @Async
    public void sendTrade(TradeRec tradeRec); 
}

