package org.civildefence.letovbot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class MyDefenceGenerator {
    private static Random random = new Random();
    private static String[][] parts = {{"солнечньнй", "траурньнй", "плюшевьнй", "бешеный", "памятный", "трепетный", "базовый", "скошенный", "преданный", "ласковый", "пойманньнй", "радужный", "огненный", "радостный", "тензорный", "шёлковый", "пепельный", "ламповый", "жареный", "загнанный"},
            {"зайчик", "Верник", "глобус", "ветер", "щавель", "пёсик", "копчик", "ландыш", "стольник", "мальчик", "дольщик", "Игорь", "невод", "егерь", "пончик", "лобстер", "жемчуг", "кольщик", "йогурт", "овод"},
            {"стеклянного", "ванильного", "резонного", "широкого", "дешёвого", "горбатого", "собачьего", "исконного", "волшебного", "картонного", "лохматого", "арбузного", "огромного", "запойного", "великого", "бараньего", "вандального", "ядрёного", "парадного", "укромного"},
            {"глаза", "плова", "Пельша", "мира", "деда", "жира", "мема", "ада", "бура", "кала", "нёба", "неба", "гунна", "хлама", "шума", "воза", "сала", "фена", "зала", "рака", "копа", "лоха"}};

    static {
        for (int i = 0; i < parts.length; i++) {
            new HashSet<String>(Arrays.asList(parts[i])).toArray(parts[i]);
        }
    }

    public String generate() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String[] part = parts[i];
            res.append(part[random.nextInt(part.length)]).append(" ");
        }
        return res.toString().trim();
    }
}
