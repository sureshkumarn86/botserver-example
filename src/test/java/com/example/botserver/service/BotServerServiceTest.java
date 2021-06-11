package com.example.botserver.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.botserver.model.IntentReply;
import com.example.botserver.repository.IntentReplyRepository;
import ultimateai.chatclient.api.IntentsApi;
import ultimateai.chatclient.model.InlineResponse200;
import ultimateai.chatclient.model.InlineResponse200Intents;
import ultimateai.chatclient.model.IntentsBody;

/**
 * Service class test
 */
@ExtendWith({MockitoExtension.class})
class BotServerServiceTest {

    @InjectMocks
    private BotServerService botServerService;

    @Mock
    private IntentReplyRepository repository;

    @Mock
    private IntentsApi intentsApi;

    String defaultReply = "AI could not give the correct answer";

    @BeforeEach
    public void init() {
        botServerService.defaultReply = defaultReply;
    }

    @Test
    void getReply() {
        Mockito.when(intentsApi.intentsPost(ArgumentMatchers.any(IntentsBody.class))).thenReturn(new InlineResponse200()
                .intents(Arrays.asList(new InlineResponse200Intents().name("Greeting").confidence(new BigDecimal(".75")))));
        Mockito.when(repository.findByNameAndConfidenceThresholdLessThanEqual("Greeting", 0.75f))
                .thenReturn(Optional.of(new IntentReply().setId("dummy").setName("Greeting")
                        .setConfidenceThreshold(.75f).setReply("Hello :) How can I help you?")));
        Assertions.assertEquals("Hello :) How can I help you?",
                botServerService.getReply(UUID.randomUUID().toString(), "Hello.").getReply());
    }

    @Test
    void getReply_HigherThreshold() {
        Mockito.when(intentsApi.intentsPost(ArgumentMatchers.any(IntentsBody.class))).thenReturn(new InlineResponse200()
                .intents(Arrays.asList(
                        new InlineResponse200Intents().name("Greeting2").confidence(new BigDecimal(".76")),
                        new InlineResponse200Intents().name("Greeting").confidence(new BigDecimal(".75"))
                )));
        Mockito.when(repository.findByNameAndConfidenceThresholdLessThanEqual("Greeting", 0.75f))
                .thenReturn(Optional.of(new IntentReply().setId("dummy").setName("Greeting")
                        .setConfidenceThreshold(.75f).setReply("Hello :) How can I help you?")));
        Mockito.when(repository.findByNameAndConfidenceThresholdLessThanEqual("Greeting2", 0.76f))
                .thenReturn(Optional.empty());
        Assertions.assertEquals("Hello :) How can I help you?",
                botServerService.getReply(UUID.randomUUID().toString(), "Hello.").getReply());
    }


    @Test
    void getReply_FailThreshold_Default_Reply() {
        Mockito.when(intentsApi.intentsPost(ArgumentMatchers.any(IntentsBody.class))).thenReturn(new InlineResponse200()
                .intents(Arrays.asList(new InlineResponse200Intents().name("Greeting").confidence(new BigDecimal(".7")))));
        Mockito.when(repository.findByNameAndConfidenceThresholdLessThanEqual("Greeting", 0.7f))
                .thenReturn(Optional.empty());
        Assertions.assertEquals(defaultReply,
                botServerService.getReply(UUID.randomUUID().toString(), "Hllo.").getReply());
    }
}
