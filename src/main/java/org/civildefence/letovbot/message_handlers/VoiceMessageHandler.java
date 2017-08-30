package org.civildefence.letovbot.message_handlers;

import lombok.SneakyThrows;
import org.civildefence.letovbot.LetovBot;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Voice;

import java.io.InputStream;

public class VoiceMessageHandler implements MessageHandler {

    @Override
    public boolean handleMessage(Message message, LetovBot bot) {
        Voice voice = message.getVoice();
        if (voice != null) {
            new Thread() {
                @Override
                @SneakyThrows
                public void run() {
                    InputStream voiceStream = bot.getFileInputStream(voice.getFileId());
                    SendAudio sendAudio = new SendAudio()
                            .setNewAudio("voice-" + message.getFrom() + "-" + message.getDate(), voiceStream)
                            .setReplyToMessageId(message.getMessageId())
                            .setChatId(message.getChatId());
                    bot.sendAudio(sendAudio);
                    bot.withStateStorage(storage -> {
                        storage.withValue(message.getChat(), message.getFrom(), "StickerMessageHandler", "" + message.getFrom().getId(), 0L,
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
