package org.civildefence.letovbot.message_handlers;

import org.civildefence.letovbot.LetovBot;
import org.telegram.telegrambots.api.objects.Message;

public interface MessageHandler {
    boolean handleMessage(Message message, LetovBot bot);
}
