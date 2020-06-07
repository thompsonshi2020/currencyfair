package com.fair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import com.fair.model.TradeRec;
import com.fair.service.TradeConsumerService;
import com.fair.config.AppConfig;
import com.fair.util.LoggerUtil;
import com.fair.util.LogLevel;

/**
 * The controller of consuming the trade records
 * It gets the Message Consumption engine from applicationContext
 */
@RestController
@RequestMapping(value = "/fair/")
public class TradeController {
    
    @Autowired
	private AppConfig appConfig;

    @Autowired
	private ApplicationContext applicationContext;


    @RequestMapping(value = "/trade", method = RequestMethod.POST, consumes = "application/json")
    public void transPost(@Valid @RequestBody TradeRec tradeRec) {
    
        LoggerUtil.logEnter(TradeController.class, LogLevel.INFO, "transPost", tradeRec);
        
        TradeConsumerService tradeConsumerService = applicationContext.getBean(appConfig.getTradeConsumer(),
                                                                                    TradeConsumerService.class);
        tradeConsumerService.sendTrade(tradeRec);  

        LoggerUtil.logExit(TradeController.class, LogLevel.INFO, "transPost");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}