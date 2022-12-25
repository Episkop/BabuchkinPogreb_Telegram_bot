package com.example.Controller;

import com.example.RabbitQueue;
import com.example.Service.Impl.UpdateProducer;
import com.example.Utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.RabbitQueue.*;

@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    //   Ниже описывается первичная валидация первичных данных
    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is NULL");
            return;// если переменная вообще пустая
        }
        if (update.getMessage() != null) {// если сообщение не пустое
            distributeMessageByType(update);
        } else {
            log.error("Unsupported message type is received" + update);
        }
    }

    //Розделение сообщений по типам :текст, файл(документ), фото. Остальное не обрабатываем и возвращаем сообщение об этом
    // с помощью класса messageUtils и метода setUnsupportedMessageTypeView
    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.getText() != null) {
            processTextMessage(update);
        } else if (message.getDocument() != null) {
            processDocMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }
// метод принимает необрабатываемый тип данных и через клас messageUtils вожвращает сообщение "Unsupported message type"
    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generatorSendMessageWithText(update, "Unsupported message type");
        setView(sendMessage);
    }

    private void setFileIsResivedView(Update update) {
        var sendMessage = messageUtils.generatorSendMessageWithText(update, "Message was received! Processed...");
        setView(sendMessage);
    }
    // передает сообщение пользователю
    private void setView(SendMessage sendMessage) {
        telegramBot.sendAnsverMassege(sendMessage);
    }
    private void processPhotoMessage(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE,update);
        setFileIsResivedView(update);
    }
    private void processDocMessage(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE,update);
        setFileIsResivedView(update);
    }
    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE,update);
    }
}
