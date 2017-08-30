package org.civildefence.letovbot;

import lombok.extern.log4j.Log4j;
import org.civildefence.letovbot.message_handlers.MessageHandler;
import org.civildefence.letovbot.utils.StateStorage;
import org.reflections.Reflections;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Log4j
public class LetovBot extends TelegramLongPollingBot {

    List<MessageHandler> handlers = new ArrayList<>();
    private String stateFileName = "state.json";

    StateStorage stateStorage = StateStorage.load(stateFileName);

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
            new Thread() {
                public void run() {
                    for (MessageHandler handler : handlers) {
                        if (handler.handleMessage(update.getMessage(), bot)) {
                            withStateStorage((storage -> {
                                Integer count = stateStorage.getInteger(update.getMessage().getChat().getId(), "LetovBot", "usages");
                                if (count == null) {
                                    count = 0;
                                }
                                count += 1;
                                stateStorage.put(update.getMessage().getChat(), update.getMessage().getFrom(), "LetovBot", "usages", count);
                            }));
                            saveStorage();
                            log.info("Handled message: " + update.getMessage());
                            return;
                        }
                    }
                }
            }.start();
        }
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

    public interface StateStorageProcessor {
        void process(StateStorage storage);
    }

    public LetovBot() throws FileNotFoundException {
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
}
