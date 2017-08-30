package org.civildefence.letovbot.message_handlers.commands;

import lombok.SneakyThrows;
import org.civildefence.letovbot.LetovBot;
import org.civildefence.letovbot.message_handlers.AdminMessageHandler;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

public class DisableInGroupsCommandHandler implements CommandHandler {
    @Override
    @SneakyThrows
    public boolean handleCommand(AdminMessageHandler adminMessageHandler, LetovBot bot, Message message) {
        if (message.getText().equalsIgnoreCase("/disableInGroups") ||
                message.getText().equalsIgnoreCase("/enableInGroups")) {
            String txt = "You are not administrator of this bot";
            if (adminMessageHandler.isAdministrator(message.getFrom().getId())) {
                boolean val = message.getText().equalsIgnoreCase("/disableInGroups");
                bot.setDisabledInGroups(val);
                if (val) {
                    txt = "Disabled annoying stuff in groups";
                } else {
                    txt = "Enabled annoying stuff in groups";
                }
            }
            SendMessage sendMessage = new SendMessage()
                    .setText(txt)
                    .setChatId(message.getChatId())
                    .setReplyToMessageId(message.getMessageId());
            bot.sendMessage(sendMessage);
            return true;
        }
        return false;
    }
}
