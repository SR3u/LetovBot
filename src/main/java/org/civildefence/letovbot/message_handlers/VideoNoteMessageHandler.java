package org.civildefence.letovbot.message_handlers;

import lombok.extern.log4j.Log4j;
import org.civildefence.letovbot.LetovBot;
import org.civildefence.letovbot.utils.StateStorage;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.VideoNote;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log4j
public class VideoNoteMessageHandler implements MessageHandler {
    @Override
    public boolean handleMessage(Message message, LetovBot bot) {
        VideoNote video = message.getVideoNote();
        if (video != null) {
            try {
                GetFile getFile = new GetFile().setFileId(video.getFileId());
                org.telegram.telegrambots.api.objects.File file = bot.execute(getFile);
                String url = file.getFileUrl(bot.getBotToken());
                InputStream input = new URL(url).openStream();
                SendVideo sendVideo = new SendVideo().setChatId(message.getChatId())
                        .setReplyToMessageId(message.getMessageId())
                        .setNewVideo(video.getFileId(), input);
                bot.sendVideo(sendVideo);

                String tag = "VideoNoteMessageHandler";
                StateStorage storage = bot.getStateStorage();
                Map<Integer, Set<String>> videosMap = (Map<Integer, Set<String>>) storage.get(message.getChatId(), tag, "videos");
                if (videosMap == null) {
                    videosMap = new HashMap<>();
                    storage.put(message.getChat(), message.getFrom(), tag, "videos", videosMap);
                }
                Set<String> videos = videosMap.get(message.getFrom().getId());
                if (videos == null) {
                    videos = new HashSet<>();
                    videosMap.put(message.getFrom().getId(), videos);
                }
                videos.add(url);
                return true;
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

}
