package org.civildefence.letovbot;

import lombok.extern.log4j.Log4j;
import org.civildefence.letovbot.message_handlers.MessageHandler;
import org.reflections.Reflections;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Log4j
public class LetovBot extends TelegramLongPollingBot {

    List<MessageHandler> handlers = new ArrayList<>();

    @Override
    public String getBotUsername() {
        return "CivilDefenceBot";
    }

    @Override
    public String getBotToken() {
        return "394603229:AAH1DSXEZbwCWiySAWYeq3x-nOtysmYnjSo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            for (MessageHandler handler : handlers) {
                if (handler.handleMessage(update.getMessage(), this)) {
                    log.info("Handled message: " + update.getMessage());
                    return;
                }
            }
        }
    }


    public LetovBot() {
        Class<? extends MessageHandler>[] handlers = getAllHandlers();
        for (Class<? extends MessageHandler> handlerClass : handlers) {
            try {
                MessageHandler handler = handlerClass.newInstance();
                this.handlers.add(handler);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static Class<? extends MessageHandler>[] getAllHandlers() {
        Reflections reflections = new Reflections("org.civildefence.letovbot");
        Set<Class<? extends MessageHandler>> subTypes = reflections.getSubTypesOf(MessageHandler.class);
        subTypes.removeIf(aClass -> Modifier.isAbstract(aClass.getModifiers()));
        Class<? extends MessageHandler>[] res = new Class[subTypes.size()];
        int i = 0;
        for (Class<? extends MessageHandler> cClass : subTypes) {
            res[i] = cClass;
            i++;
        }
        return res;
    }
}
