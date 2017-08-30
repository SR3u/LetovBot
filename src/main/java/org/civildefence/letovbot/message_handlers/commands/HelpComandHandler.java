package org.civildefence.letovbot.message_handlers.commands;

import lombok.SneakyThrows;
import org.civildefence.letovbot.LetovBot;
import org.civildefence.letovbot.message_handlers.AdminMessageHandler;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

public class HelpComandHandler implements CommandHandler {
    @Override
    @SneakyThrows
    public boolean handleCommand(AdminMessageHandler adminMessageHandler, LetovBot bot, Message message) {
        if (message.hasText()) {
            if (message.getText().equalsIgnoreCase("/help") || message.getText().equalsIgnoreCase("/start")) {
                String txt = "This bot can help you download video notes, stickers and voice messages.\nAnd also it can sing \"MOYA OBORONA\".";
                SendMessage sendMessage = new SendMessage()
                        .setText(txt)
                        .setChatId(message.getChatId())
                        .setReplyToMessageId(message.getMessageId());
                bot.sendMessage(sendMessage);
                return true;
            }
        }
        return false;
    }
}
