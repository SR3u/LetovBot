package org.civildefence.letovbot.message_handlers;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

public interface MessageHandler {
    boolean handleMessage(Message message, AbsSender sender);
}
