package org.civildefence.letovbot.message_handlers.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OooooStringUtil {
    public static boolean isOoooo(String string) {
        String[] strings = string.split("\\s+");
        if (strings.length < 1) {
            return false;
        }
        string = strings[strings.length - 1];
        Pattern pattern = Pattern.compile("[oо○]{3,}");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
}
