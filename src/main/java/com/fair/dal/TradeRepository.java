package com.fair.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.fair.model.TradeRec;

/**
 * The interface of TradeRepository
 */

public interface TradeRepository extends MongoRepository<TradeRec, String> {

}