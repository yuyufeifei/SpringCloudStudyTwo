package com.gzh.springcloud.service.impl;

import com.gzh.springcloud.service.IMessageProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.util.UUID;

/**
 * @author GZH
 * @date 2022-03-14
 */
@EnableBinding(Source.class)
@Log4j2
public class MessageProviderImpl implements IMessageProvider {

    /**
     * 消息发送管道
      */
    private final MessageChannel output;
    public MessageProviderImpl(MessageChannel output) {
        this.output = output;
    }

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        log.info(serial);
        return serial;
    }
}
