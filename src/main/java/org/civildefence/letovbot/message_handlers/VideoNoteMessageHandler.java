package org.civildefence.letovbot.message_handlers;

import lombok.extern.log4j.Log4j;
import org.civildefence.letovbot.LetovBot;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.VideoNote;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.*;

@Log4j
public class VideoNoteMessageHandler implements MessageHandler {
    @Override
    public boolean handleMessage(Message message, LetovBot bot) {
        VideoNote video = message.getVideoNote();
        if (video != null) {
            try {
                InputStream input = bot.getFileInputStream(video.getFileId());
                SendVideo sendVideo = new SendVideo().setChatId(message.getChatId())
                        .setReplyToMessageId(message.getMessageId())
                        .setNewVideo(video.getFileId(), input);
                bot.sendVideo(sendVideo);

                String tag = "VideoNoteMessageHandler";
                bot.withStateStorage((storage -> {
                    Map<String, Set<String>> videosMap = (Map<String, Set<String>>) storage.get(message.getChatId(), tag, "videos");
                    if (videosMap == null) {
                        videosMap = new HashMap<>();
                        storage.put(message.getChat(), message.getFrom(), tag, "videos", videosMap);
                    }
                    Set<String> videos = new HashSet<>();
                    Object o = videosMap.get("" + message.getFrom().getId());
                    if (o != null) {
                        videos = new HashSet<>((Collection) o);
                    }
                    if (videos == null) {
                        videos = new HashSet<>();
                    }
                    videos.add(bot.getFileURL(bot.getFile(video.getFileId())));
                    videosMap.put("" + message.getFrom().getId(), videos);
                    storage.put(message.getChat(), message.getFrom(), tag, "videos", videosMap);
                    bot.saveStorage();
                }));
                return true;
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

}
