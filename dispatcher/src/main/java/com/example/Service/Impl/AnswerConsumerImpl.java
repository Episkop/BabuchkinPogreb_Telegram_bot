package com.example.Service.Impl;

import com.example.Controller.UpdateController;
import com.example.Service.AnsverConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.RabbitQueue.ANSVER_MESSAGE;

@Service
public class AnswerConsumerImpl implements AnsverConsumer {
    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSVER_MESSAGE)
    public void consumer(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
