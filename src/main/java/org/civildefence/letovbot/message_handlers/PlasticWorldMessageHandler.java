package org.civildefence.letovbot.message_handlers;

import org.civildefence.letovbot.LetovBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class PlasticWorldMessageHandler implements MessageHandler {
    @Override
    public boolean handleMessage(Message message, LetovBot bot) {
        if (message.hasText()) {
            String[] words = message.getText().trim().split("\\s+");
            if (words.length == 1) {
                if (words[0].equalsIgnoreCase("Пластмассовый")) {
                    SendMessage newMessage = new SendMessage() // Create a SendMessage object with mandatory fields
                            .setChatId(message.getChatId())
                            .setParseMode("Markdown")
                            .setText("Пластмассовый мир победил...")
                            .setReplyToMessageId(message.getMessageId());
                    try {
                        bot.sendMessage(newMessage); // Call method to send the message
                        return true;
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean disableInGroups() {
        return true;
    }
}
