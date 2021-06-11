package com.example.botserver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "intent_reply")
public class IntentReply {
    @Id
    private String id;
    private String name;
    private String reply;
    private Float confidenceThreshold;
//    private List<String> messageCache;

    public String getName() {
        return name;
    }

    public IntentReply setName(String name) {
        this.name = name;
        return this;
    }

    public String getReply() {
        return reply;
    }

    public IntentReply setReply(String reply) {
        this.reply = reply;
        return this;
    }

    public String getId() {
        return id;
    }

    public IntentReply setId(String id) {
        this.id = id;
        return this;
    }

    public Float getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public IntentReply setConfidenceThreshold(Float confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
        return this;
    }
}
