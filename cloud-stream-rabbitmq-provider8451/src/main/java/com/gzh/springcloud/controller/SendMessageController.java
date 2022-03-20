package com.gzh.springcloud.controller;

import com.gzh.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GZH
 * @date 2022-03-14
 */
@RestController
public class SendMessageController {

    private final IMessageProvider messageProvider;
    public SendMessageController(IMessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @GetMapping("/sendMessage")
    public String sendMessage() {
        return messageProvider.send();
    }

}
