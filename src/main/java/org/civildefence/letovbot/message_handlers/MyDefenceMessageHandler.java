package org.civildefence.letovbot.message_handlers;

import org.civildefence.letovbot.LetovBot;
import org.civildefence.letovbot.message_handlers.utils.MyDefenceGenerator;
import org.civildefence.letovbot.message_handlers.utils.OooooStringUtil;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MyDefenceMessageHandler implements MessageHandler {

    @Override
    public boolean handleMessage(Message message, LetovBot bot) {
        if (message.hasText()) {
            String text = message.getText().toLowerCase();
            if (OooooStringUtil.isOoooo(text)) {
                SendMessage newMessage = new SendMessage() // Create a SendMessage object with mandatory fields
                        .setChatId(message.getChatId())
                        .setParseMode("Markdown")
                        .setText("*МОЯ ОБОРОНА!!!*\n_" + new MyDefenceGenerator().generate() + "_")
                        .setReplyToMessageId(message.getMessageId());
                try {
                    bot.sendMessage(newMessage); // Call method to send the message
                    return true;
                } catch (TelegramApiException e) {
                    e.printStackTrace();
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
