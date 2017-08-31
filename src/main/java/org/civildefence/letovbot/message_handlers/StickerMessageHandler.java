package org.civildefence.letovbot.message_handlers;

import lombok.SneakyThrows;
import org.civildefence.letovbot.LetovBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.stickers.Sticker;

import java.io.InputStream;
import java.net.URL;

public class StickerMessageHandler implements MessageHandler {
    @Override
    public boolean handleMessage(Message message, LetovBot bot) {
        Sticker sticker = message.getSticker();
        if (sticker != null) {
            new Thread() {
                @Override
                @SneakyThrows
                public void run() {
                    String url = bot.getFileURL(bot.getFile(sticker.getFileId()));
                    SendMessage sendMessage = new SendMessage()
                            .setReplyToMessageId(message.getMessageId())
                            .setChatId(message.getChatId())
                            .setText(url);
                    InputStream stickerStream = new URL(url).openStream();
                    SendPhoto sendPhoto = new SendPhoto()
                            .setNewPhoto(sticker.getEmoji(), stickerStream)
                            .setReplyToMessageId(message.getMessageId())
                            .setChatId(message.getChatId());
                    //bot.sendMessage(sendMessage);
                    bot.sendPhoto(sendPhoto);
                    bot.withStateStorage(storage -> {
                        storage.withValue(message.getChat(), message.getFrom(), "StickerMessageHandler", sticker.getSetName(), 0L,
                                value -> {
                                    Long res = (Long) value;
                                    res += 1;
                                    return res;
                                });
                    });
                    bot.saveStorage();
                }
            }.start();
            return true;
        }
        return false;
    }

    @Override
    public boolean disableInGroups() {
        return true;
    }
}
