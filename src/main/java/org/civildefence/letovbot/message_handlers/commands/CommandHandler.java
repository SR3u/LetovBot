package org.civildefence.letovbot.message_handlers.commands;

import org.civildefence.letovbot.LetovBot;
import org.civildefence.letovbot.message_handlers.AdminMessageHandler;
import org.telegram.telegrambots.api.objects.Message;

public interface CommandHandler {
    boolean handleCommand(AdminMessageHandler adminMessageHandler, LetovBot bot, Message message);
}
