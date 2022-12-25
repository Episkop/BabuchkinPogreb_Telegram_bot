package com.example.Service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProduser {
    void produce (String rebbitQueue, Update update);
}
