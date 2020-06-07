package com.fair.service;

import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Data;

import com.fair.util.LoggerUtil;
import com.fair.util.LogLevel;

/**
 * The service of Web Socket 
 */

@Data
@Service
public class SocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public void sendTradeResult(String destination, Object sendObject) {

        LoggerUtil.logEnter(SocketService.class, LogLevel.INFO, "sendTradeResult");

        try {        
            simpMessagingTemplate.convertAndSend(destination, sendObject);
        
        } catch (Exception e) {
            LoggerUtil.logError(SimpMessagingTemplate.class, "Error occurred: ", e);
        }

        LoggerUtil.logExit(SocketService.class, LogLevel.INFO, "sendTradeResult");

    }

}