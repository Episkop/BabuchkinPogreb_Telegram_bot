package com.example.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnsverConsumer {
    void consumer (SendMessage sendMessage);
}
