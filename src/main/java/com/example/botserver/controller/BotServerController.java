package com.example.botserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.botserver.dto.ReplyDto;
import com.example.botserver.service.BotServerService;


/**
 * The Controller
 */
@RestController
@RequestMapping("/api/bot-server")
public class BotServerController {

    private final BotServerService botServerService;

    /**
     * @param botServerService the service instance
     */
    public BotServerController(BotServerService botServerService) {
        this.botServerService = botServerService;
    }


    /**
     * gets the reply for the visitor message
     * @param botId The bot id for calling the AI api.
     * @param visitorMessage The visitor message
     * @return reply for the visitor message.
     */
    @GetMapping("/")
    public ResponseEntity<ReplyDto> getReply(@RequestParam String botId, @RequestParam String visitorMessage) {
        return ResponseEntity.ok(botServerService.getReply(botId, visitorMessage));
    }
}
