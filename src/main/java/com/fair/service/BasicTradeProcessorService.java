package com.fair.service;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.fair.model.TradeRec;
import com.fair.dto.ByCountryAggregate;
import com.fair.util.LogLevel;
import com.fair.util.LoggerUtil;

/**
 * The implementation of trade processor
 * It sends the trade result to frontend by by web socket:
 *   i) getAllTradeAggregate()
 *   ii) getLatestTrade()
 */

@Service("basic")
public class BasicTradeProcessorService implements TradeProcessorService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	SocketService socketService;

    private static final String WS_LATEST_TRADE_DESTINATION = "/topic/latestTrade";
    private static final String WS_AGGREGATE_TRADE_DESTINATION = "/topic/aggregateTrade";

    @Override
    public void processTrade() {

        LoggerUtil.logEnter(BasicTradeProcessorService.class, LogLevel.INFO, "BasicTradeProcessorService");

        getLatestTrade();
        getAllTradeAggregate();
    }

    /**
     * Get top 10 latest processed trade messages
     */
    private void getLatestTrade() {

        LoggerUtil.logEnter(BasicTradeProcessorService.class, LogLevel.INFO, "BasicTradeProcessorService.getLatestTrade");

        try {
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.DESC, "timeCreated"));
            query.limit(10);
            List<TradeRec> allTradeResult = mongoTemplate.find(query, TradeRec.class);

            socketService.sendTradeResult(WS_LATEST_TRADE_DESTINATION, allTradeResult);
       
        } catch (Exception e) {
            LoggerUtil.logError(TradeService.class, "Error occurred: ", e);
        }
    }

    /**
     * Get no. of trade messages grouping by country
     */
    private List<ByCountryAggregate> getAllTradeAggregate() {

		long start = System.currentTimeMillis();
        Aggregation agg;
        List<ByCountryAggregate> resultAggregate = new ArrayList<>();
        List<AggregationOperation> operations = new ArrayList<>();

        LoggerUtil.logEnter(BasicTradeProcessorService.class, LogLevel.INFO, "BasicTradeProcessorService.getAllTradeAggregate");

        try {
            operations.add(group("originatingCountry").count().as("tradeCount").push("currencyPair").as("pairs"));
            operations.add(project("tradeCount", "pairs").and("_id").as("originatingCountry"));
            operations.add(sort(Sort.Direction.DESC,"tradeCount", "originatingCountry"));
            operations.add(limit(10));

            agg = newAggregation(operations);

            AggregationResults<ByCountryAggregate> results = mongoTemplate.aggregate(agg, TradeRec.class, ByCountryAggregate.class);
            resultAggregate = results.getMappedResults();

            long runTime = System.currentTimeMillis() - start;
            LoggerUtil.logDebug(BasicTradeProcessorService.class, "Took {}ms to retrieve country aggregate", runTime);

            socketService.sendTradeResult(WS_AGGREGATE_TRADE_DESTINATION, resultAggregate);

        } catch (Exception e) {
            LoggerUtil.logError(TradeService.class, "Error occurred: ", e);
        }

        return resultAggregate;

    }

}
