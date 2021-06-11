package com.example.botserver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.botserver.model.IntentReply;

/**
 * IntentReplyRepository. The repository interface.
 */
@Repository
public interface IntentReplyRepository extends MongoRepository<IntentReply, String> {
    /**
     * finds Reply by name and confidence threshold less than equal to.
     * @param name of the reply
     * @param threshold confident of the message. should be minimum threshold of the configuration.
     * @return the IntentReply if match found.
     */
    Optional<IntentReply> findByNameAndConfidenceThresholdLessThanEqual(String name, Float threshold);
}
