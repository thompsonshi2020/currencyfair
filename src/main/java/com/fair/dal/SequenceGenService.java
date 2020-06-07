package com.fair.dal;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.fair.model.DbSequence;
import com.fair.util.LoggerUtil;

/**
 * The service of getting the sequence number from mongoDB
 */

@Service
public class SequenceGenService {

    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGenService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        try {
            DbSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                    new Update().inc("seq",1), options().returnNew(true).upsert(true),
                    DbSequence.class);

            return !Objects.isNull(counter) ? counter.getSeq() : 1;

        } catch (Exception e) {
            LoggerUtil.logError(SequenceGenService.class, "Error occurred: ", e);
        }
            return -1;
  
    }
}    