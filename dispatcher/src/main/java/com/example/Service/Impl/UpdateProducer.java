package com.example.Service.Impl;

import com.example.Service.UpdateProduser;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
@Log4j
@Service
public class UpdateProducer implements UpdateProduser {
    private final RabbitTemplate rabbitTemplate;

    public UpdateProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String rebbitQueue, Update update) {
        log.debug(update.getMessage().getText());
        rabbitTemplate.convertAndSend(rebbitQueue,update);
    }
}
