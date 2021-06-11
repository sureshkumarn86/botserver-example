package com.example.botserver.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.botserver.dto.ReplyDto;
import com.example.botserver.model.IntentReply;
import com.example.botserver.repository.IntentReplyRepository;
import ultimateai.chatclient.api.IntentsApi;
import ultimateai.chatclient.model.InlineResponse200Intents;
import ultimateai.chatclient.model.IntentsBody;

/**
 * The Service
 */
@Service
public class BotServerService {
    private final IntentReplyRepository intentReplyRepository;
    private final IntentsApi intentsApi;
    @Value("${botserver.default-reply}")
    String defaultReply;

    public BotServerService(IntentReplyRepository intentReplyRepository, IntentsApi intentsApi) {
        this.intentReplyRepository = intentReplyRepository;
        this.intentsApi = intentsApi;
    }

    /**
     * It returns a reply corresponding to the highest predicted intent above the confidence threshold configured.
     * It uses ultimate.ai <a href="https://chat.ultimate.ai/api">API</a> to retrieve the list of predicted intents for a given message.
     *
     * @param botId          The bot id for calling the AI api.
     * @param visitorMessage The visitor message
     * @return the reply for the visitor message. <i>AI could not give the correct answer</i> is the default reply message if no matching reply found.
     */
    public ReplyDto getReply(String botId, String visitorMessage) {
        // compose request object to send AI api.
        IntentsBody aiRequest = new IntentsBody().botId(botId).message(visitorMessage);
        List<InlineResponse200Intents> aiIntents = intentsApi.intentsPost(aiRequest).getIntents();
        // sort by best confidence
        aiIntents.sort((inlineResponse200Intents, t1) -> t1.getConfidence().compareTo(inlineResponse200Intents.getConfidence()));
        for (InlineResponse200Intents aiIntent : aiIntents) {
            // find the reply for the name and confidence meets threshold configured.
            // return if matching reply found. else continue loop.
            float aiConfidence = aiIntent.getConfidence().floatValue();
            Optional<IntentReply> reply = intentReplyRepository.findByNameAndConfidenceThresholdLessThanEqual(aiIntent.getName(), aiConfidence);
            if (reply.isPresent()) {
                return reply.map(intentReply -> {
                    //map the document to dto
                    ReplyDto replyDto = new ReplyDto();
                    replyDto.setReply(intentReply.getReply());
                    return replyDto;
                }).get();
            }
        }
        //if no matching reply found, return with default reply.
        return new ReplyDto().setReply(defaultReply);
    }
}
