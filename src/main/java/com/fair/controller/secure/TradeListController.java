package com.fair.controller.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fair.config.AppConfig;
import com.fair.service.TradeProcessorService;
import com.fair.util.LogLevel;
import com.fair.util.LoggerUtil;

/**
 * The controller of process the trade records
 * It gets the process message engine from applicationContext
 * It runs regularly (every 5 seconds) to send out the prcoessed records 
 */

@Controller
public class TradeListController {
    
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AppConfig appConfig;

    private boolean initLoad;

    @RequestMapping(value="/")
    public String getIndex() {
        
        LoggerUtil.logEnter(TradeListController.class, LogLevel.INFO, "getIndex");
        return "index";
    }

    @RequestMapping(value = "/loadtrade", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String loadTrade() {

        LoggerUtil.logEnter(TradeListController.class, LogLevel.INFO, "loadTrade");

            processData();
            this.initLoad = true;

            LoggerUtil.logExit(TradeListController.class, LogLevel.INFO, "loadTrade");

            return "Success";
    }

    //It runs regularly (every 5 seconds) to send out the prcoessed records 
    @Scheduled(fixedDelay=5000)
    public void sendNotification() {

        LoggerUtil.logEnter(TradeListController.class, LogLevel.DEBUG, "sendNotification");

        if (initLoad)
            processData();

        LoggerUtil.logExit(TradeListController.class, LogLevel.DEBUG, "sendNotifications");
    }

    private void processData() {

        LoggerUtil.logEnter(TradeListController.class, LogLevel.DEBUG, "processData");

        try {
            TradeProcessorService tradeProcessService = (TradeProcessorService)applicationContext.getBean(appConfig.gettradeProcessor());
            tradeProcessService.processTrade();

        } catch (Exception e) {
            LoggerUtil.logError(TradeListController.class, "Error Occurred", e);
        }

        LoggerUtil.logExit(TradeListController.class, LogLevel.DEBUG, "processData");
    }
}