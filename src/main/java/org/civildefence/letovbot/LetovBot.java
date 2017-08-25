package org.civildefence.letovbot;

import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


@Log4j
public class LetovBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "CivilDefencBot";
    }

    @Override
    public String getBotToken() {
        return "394603229:AAH1DSXEZbwCWiySAWYeq3x-nOtysmYnjSo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("Received message: "+update.getMessage());
            String text = update.getMessage().getText().toLowerCase();
            int oldLength = text.length();
            text = OooooStringUtil.process(text);
            if(text.length() == 0 && oldLength >= 3){
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                        .setChatId(update.getMessage().getChatId())
                        .setText("МОЯ ОБОРОНА!!!").setReplyToMessageId(update.getMessage().getMessageId());
                try {
                    sendMessage(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
