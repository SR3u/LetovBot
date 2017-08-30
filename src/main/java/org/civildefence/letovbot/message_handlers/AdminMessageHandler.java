package org.civildefence.letovbot.message_handlers;

import org.civildefence.letovbot.LetovBot;
import org.civildefence.letovbot.message_handlers.commands.CommandHandler;
import org.reflections.Reflections;
import org.telegram.telegrambots.api.objects.Message;

import java.lang.reflect.Modifier;
import java.util.*;

public class AdminMessageHandler implements MessageHandler {
    private final List<CommandHandler> handlers = new ArrayList<>();
    Set<Integer> administrators = new HashSet<>(Arrays.asList(68603732));

    @Override
    public boolean handleMessage(Message message, LetovBot bot) {
        String text = message.getText();
        if (text != null && text.startsWith("/")) {
            this.handlers.forEach(commandHandler -> commandHandler.handleCommand(this, bot, message));
            return true;
        }
        return false;
    }

    @Override
    public boolean disableInGroups() {
        return false;
    }

    public boolean isAdministrator(Integer userId) {
        return administrators.contains(userId);
    }

    public AdminMessageHandler() {
        Class<? extends CommandHandler>[] handlers = getAllHandlers();
        for (Class<? extends CommandHandler> handlerClass : handlers) {
            try {
                CommandHandler handler = handlerClass.newInstance();
                this.handlers.add(handler);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static Class<? extends CommandHandler>[] getAllHandlers() {
        Reflections reflections = new Reflections("org.civildefence.letovbot");
        Set<Class<? extends CommandHandler>> subTypes = reflections.getSubTypesOf(CommandHandler.class);
        subTypes.removeIf(aClass -> Modifier.isAbstract(aClass.getModifiers()));
        Class<? extends CommandHandler>[] res = new Class[subTypes.size()];
        int i = 0;
        for (Class<? extends CommandHandler> cClass : subTypes) {
            res[i] = cClass;
            i++;
        }
        return res;
    }
}
