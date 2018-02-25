package org.civildefence.letovbot;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.civildefence.letovbot.message_handlers.MessageHandler;
import org.civildefence.letovbot.utils.StateStorage;
import org.reflections.Reflections;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Log4j
public class LetovBot extends TelegramLongPollingBot {

    private List<MessageHandler> handlers = new ArrayList<>();
    private String stateFileName = "state.json";

    private StateStorage stateStorage = StateStorage.load(stateFileName);
    private boolean disabledInGroups = true;

    @Override
    public String getBotUsername() {
        return "CivilDefenceBot";
    }

    @Override
    public String getBotToken() {
        return "394603229:AAH1DSXEZbwCWiySAWYeq3x-nOtysmYnjSo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage() != null) {
            LetovBot bot = this;
            new Thread(() -> {
                Chat chat = update.getMessage().getChat();
                for (MessageHandler handler : handlers) {
                    if (disabledInGroups && (chat.isGroupChat() || chat.isSuperGroupChat()) && handler.disableInGroups()) {
                        continue;
                    }
                    if (handler.handleMessage(update.getMessage(), bot)) {
                        withStateStorage((storage -> {
                            Integer count = stateStorage.getInteger(chat.getId(), "LetovBot", "usages");
                            if (count == null) {
                                count = 0;
                            }
                            count += 1;
                            stateStorage.put(chat, update.getMessage().getFrom(), "LetovBot", "usages", count);
                        }));
                        saveStorage();
                        log.info("Handled message: " + update.getMessage());
                        return;
                    }
                }
            }).start();
        }
    }

    public <T extends MessageHandler> T getMessageHandler(Class<T> type) {
        for (MessageHandler handler : handlers) {
            if (handler.getClass() == type) {
                return (T) handler;
            }
        }
        throw new IllegalStateException("Something wrong with MessageHandler " + type);
    }

    public void withStateStorage(StateStorageProcessor p) {
        synchronized (this) {
            p.process(stateStorage);
        }
    }

    public void saveStorage() {
        this.withStateStorage(storage -> {
            try {
                storage.save(new FileOutputStream(stateFileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void setDisabledInGroups(boolean disabledInGroups) {
        this.disabledInGroups = disabledInGroups;
    }

    public boolean getDisabledInGroups() {
        return disabledInGroups;
    }

    public interface StateStorageProcessor {
        void process(StateStorage storage);
    }

    public LetovBot() {
        Class<? extends MessageHandler>[] handlers = getAllHandlers();
        for (Class<? extends MessageHandler> handlerClass : handlers) {
            try {
                MessageHandler handler = handlerClass.newInstance();
                this.handlers.add(handler);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static Class<? extends MessageHandler>[] getAllHandlers() {
        Reflections reflections = new Reflections("org.civildefence.letovbot");
        Set<Class<? extends MessageHandler>> subTypes = reflections.getSubTypesOf(MessageHandler.class);
        subTypes.removeIf(aClass -> Modifier.isAbstract(aClass.getModifiers()));
        Class<? extends MessageHandler>[] res = new Class[subTypes.size()];
        int i = 0;
        for (Class<? extends MessageHandler> cClass : subTypes) {
            res[i] = cClass;
            i++;
        }
        return res;
    }

    @SneakyThrows
    public InputStream getFileInputStream(String fileId) {
        File file = getFile(fileId);
        String url = getFileURL(file);
        return new URL(url).openStream();;
    }

    @SneakyThrows
    public String getFileURL(File file) {
        return file.getFileUrl(this.getBotToken());
    }

    @SneakyThrows
    public File getFile(String fileId) {
        GetFile getFile = new GetFile().setFileId(fileId);
        return this.execute(getFile);
    }


}
