package org.civildefence.letovbot.message_handlers.utils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class L33tSp34kTr4nsl4t3r {
    private String originalValue = "";

    public L33tSp34kTr4nsl4t3r(String value) {
        originalValue = translateFrom(Dictionaries.getCs(), value);
        originalValue = translateFrom(Dictionaries.getL33t(), originalValue);
    }

    public String toL33t() {
        String temp = translateTo(Dictionaries.getRu2En(), originalValue.toUpperCase());
        return translateTo(Dictionaries.getL33t(), temp);
    }

    public String toNormal() {
        return originalValue;
    }

    public String toCS() {
        return translateTo(Dictionaries.getCs(), originalValue);
    }

    public static String translateTo(Dictionary dictionary, String string) {
        String[] res = new String[1];
        res[0] = string;
        dictionary.keySet().forEach(key -> {
            List<String> values = dictionary.get(key);
            if (values != null) {
                String value = values.get(random.nextInt(values.size()) % values.size());
                res[0] = res[0].replaceAll(key, value);
            }
        });
        return res[0];
    }

    public static String translateFrom(Dictionary dictionary, String string) {
        String[] res = new String[1];
        res[0] = string;
        dictionary.keySet().stream().sorted(Comparator.reverseOrder()).forEach(key -> {
            List<String> values = dictionary.get(key);
            if (values != null) {
                values.forEach(value -> {
                    if (value.equals("bl")) {
                        System.out.println(value);
                    }
                    res[0] = res[0].replaceAll(value, key);
                });
            }
        });
        return res[0];
    }

    private static Random random = new Random(new Date().getTime());

}
