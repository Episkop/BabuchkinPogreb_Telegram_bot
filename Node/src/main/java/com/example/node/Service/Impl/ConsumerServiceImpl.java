package com.example.node.Service.Impl;

import com.example.node.Service.ConsumerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.RabbitQueue.*;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final ProducerServiceImpl producerService;

    public ConsumerServiceImpl(ProducerServiceImpl producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener (queues = TEXT_MESSAGE_UPDATE)
    public void consumerTextMessageUpdate(Update update) {
        log.debug("NODE: Text message is received");

        var message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from NODE!");
        producerService.producerAnsver(sendMessage);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumerDocMessageUpdate(Update update) {
        log.debug("NODE: Doc message is received");
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumerPhotoMessageUpdate(Update update) {
        log.debug("NODE: Photo message is received");
    }
}
