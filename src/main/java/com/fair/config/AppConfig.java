package com.fair.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Application config gets properties from application.properties file
 * Specify the engine of Message Consumption which consumes the trade messages
 * Specify the engine of processing the trade message for frontend presentation
 * 1) app.tradeConsumer=rabbitmq
 * 2) app.tradeProcessor=basic
 */

@Component
@ConfigurationProperties(prefix="app")
public class AppConfig {
    
    private String tradeConsumer;
    private String tradeProcessor;
    
    public String getTradeConsumer() {
        return tradeConsumer;
    }

    public void setTradeConsumer(String tradeConsumer) {
        this.tradeConsumer = tradeConsumer;
    }

    public String gettradeProcessor() {
        return tradeProcessor;
    }

    public void setTradeProcessor(String tradeProcessor) {
        this.tradeProcessor = tradeProcessor;
    }
}