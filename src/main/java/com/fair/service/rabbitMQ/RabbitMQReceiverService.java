package com.fair.service.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fair.util.LoggerUtil;
import com.fair.model.TradeRec;
import com.fair.service.TradeService;
import com.fair.service.SocketService;

/**
 * The service of RabbitMQ messaging 
 * 1) It listens to the queue and process the trade messages
 * 2) Then, it sends the processed result to the web socket clients
 */

@Component
public class RabbitMQReceiverService {

    private TradeService tradeService;
    private SocketService socketService;
    

    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/marketTrade";    

    RabbitMQReceiverService(SocketService socketService, TradeService tradeService) {
        this.socketService = socketService;
        this.tradeService = tradeService;
    }

    @RabbitListener(queues = "fair.queue")
    public void processTrade(TradeRec tradeRec) {
        
        try {
            tradeRec = tradeService.processTrade(tradeRec);
        
            socketService.sendTradeResult(WS_MESSAGE_TRANSFER_DESTINATION, tradeRec);

        } catch (Exception e) {
            LoggerUtil.logError(RabbitMQReceiverService.class, "Error occurred: ", e);

        }
    
    }

}