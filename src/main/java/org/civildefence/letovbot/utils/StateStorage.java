package org.civildefence.letovbot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StateStorage {
    private Map<Long, Map<String, Map<String, Object>>> storage = new HashMap<>();
    private Map<Integer, User> users = new HashMap<>();
    private Map<Long, Chat> chats = new HashMap<>();
    public static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private StateStorage() {
    }


    public Object get(Long chatId, String type, String key) {
        Map<String, Map<String, Object>> typeMap = storage.get(chatId);
        if (typeMap == null) {
            return null;
        }
        Map<String, Object> typeStorage = typeMap.get(type);
        if (typeStorage == null) {
            return null;
        }
        return typeStorage.get(key);
    }

    public void put(Chat chat, User user, String type, String key, Object value) {
        if (chat == null) {
            return;
        }
        Long chatId = chat.getId();
        if (user != null) {
            users.put(user.getId(), user);
        }
        chats.put(chatId, chat);
        Map<String, Map<String, Object>> typeMap = storage.get(chatId);
        if (typeMap == null) {
            typeMap = new HashMap<>();
            storage.put(chatId, typeMap);
        }
        Map<String, Object> typeStorage = typeMap.get(type);
        if (typeStorage == null) {
            typeStorage = new HashMap<>();
            typeMap.put(type, typeStorage);
        }
        typeStorage.put(key, value);
    }

    public void remove(Integer chatId, String type, String key) {
        Map<String, Map<String, Object>> typeMap = storage.get(chatId);
        if (typeMap == null) {
            return;
        }
        Map<String, Object> typeStorage = typeMap.get(type);
        if (typeStorage == null) {
            return;
        }
        typeStorage.remove(key);
    }

    public String getString(Long chatId, String type, String key) {
        Object o = get(chatId, type, key);
        if (o instanceof String) {
            return (String) o;
        }
        return null;
    }

    public Integer getInteger(Long chatId, String type, String key) {
        Object o = get(chatId, type, key);
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    public Double getDouble(Long chatId, String type, String key) {
        Object o = get(chatId, type, key);
        if (o instanceof Double) {
            return (Double) o;
        }
        return null;
    }

    public interface ValueProcessor<T> {
        T process(T value);
    }

    @SneakyThrows
    public <T> void withValue(Chat chat, User user, String type, String key, T defaultValue, ValueProcessor<T> valueProcessor) {
        T o = (T) get(chat.getId(), type, key);
        if (o == null) {
            o = defaultValue;
            put(chat, user, type, key, o);
        }
        if (o instanceof Double) {
            if (defaultValue instanceof Long) {
                Long l = 0 + (long) ((Double) o).doubleValue();
                o = (T) l;
            }
            if (defaultValue instanceof Integer) {
                Integer l = 0 + (int) ((Double) o).doubleValue();
                o = (T) l;
            }
        }
        o = valueProcessor.process(o);
        put(chat, user, type, key, o);
    }


    public void save(OutputStream out) {
        String json = gson.toJson(this);

        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print(json);
        printWriter.flush();
    }

    public static StateStorage load(InputStream in) {

        try {
            String json = IOUtils.toString(in);
            StateStorage stateStorage = gson.fromJson(json, StateStorage.class);
            if (stateStorage != null) {
                return stateStorage;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new StateStorage();
    }

    public static StateStorage load(String fileName) {
        try {
            return load(new FileInputStream((fileName)));
        } catch (FileNotFoundException e) {
            return new StateStorage();
        }
    }
}
