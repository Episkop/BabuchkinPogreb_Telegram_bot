package com.example.node.Service.Impl;

import com.example.node.Service.ProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.RabbitQueue.ANSVER_MESSAGE;

@Service
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void producerAnsver(SendMessage sendMessage) {
    rabbitTemplate.convertAndSend(ANSVER_MESSAGE,sendMessage);
    }
}
