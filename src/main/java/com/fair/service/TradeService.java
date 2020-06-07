package com.fair.service;

import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fair.model.TradeRec;
import com.fair.dal.TradeRepository;
import com.fair.dal.SequenceGenService;
import com.fair.util.LoggerUtil;

/**
 * The service of process the trade messages, includes:
 *   i) Validate the trade messages 
 *   ii) Store the trade messages to the specific Data Access Layer
 */

@Service
@Validated
public class TradeService {

    private final TradeRepository tradeRepository;
    private final SequenceGenService sequenceGenerator;


    TradeService(TradeRepository tradeRepository, SequenceGenService sequenceGenerator) {
        this.tradeRepository = tradeRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public TradeRec processTrade(TradeRec tradeRec) {

        try {
            //Validate the transaction
            validateTrade(tradeRec);
            
            // Set a trade ID from each transaction
            tradeRec.setTradeId(sequenceGenerator.generateSequence(TradeRec.SEQUENCE_NAME));

            // Reorder currency FRM and TO by alphabetical order
            tradeRec.setCurrencyPair("");
            
            Date now = new Date();  
            tradeRec.setTimeCreated(now);

            tradeRepository.save(tradeRec);
        
        } catch (Exception e) {
            LoggerUtil.logError(TradeService.class, "Error occurred: ", e);
        }

        return tradeRec;
    }

    /**
     *  Specify the logic here to validate the transaction, such as:
     *  i) the rate of currencyFrom and currencyTo is valid in certain time period
     *  ii) the amountSell and amount Buy are vaild
     */   
    private void validateTrade(TradeRec tradeRec) {
        try {

            tradeRec.setTradeSuccess(true);
        
        } catch (Exception e) {
            LoggerUtil.logError(TradeService.class, "Error occurred: ", e);
        }       
    }
    
}