package org.civildefence.letovbot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MyDefenceGenerator {
    private static final Random random = new Random();
    private static final String[][] parts = {{"солнечньнй", "траурньнй", "плюшевьнй", "бешеный", "памятный", "трепетный", "базовый", "скошенный", "преданный", "ласковый", "пойманньнй", "радужный", "огненный", "радостный", "тензорный", "шёлковый", "пепельный", "ламповый", "жареный", "загнанный"},
            {"зайчик", "Верник", "глобус", "ветер", "щавель", "пёсик", "копчик", "ландыш", "стольник", "мальчик", "дольщик", "Игорь", "невод", "егерь", "пончик", "лобстер", "жемчуг", "кольщик", "йогурт", "овод"},
            {"стеклянного", "ванильного", "резонного", "широкого", "дешёвого", "горбатого", "собачьего", "исконного", "волшебного", "картонного", "лохматого", "арбузного", "огромного", "запойного", "великого", "бараньего", "вандального", "ядрёного", "парадного", "укромного"},
            {"глаза", "плова", "Пельша", "мира", "деда", "жира", "мема", "ада", "бура", "кала", "нёба", "неба", "гунна", "хлама", "шума", "воза", "сала", "фена", "зала", "рака", "копа", "лоха"}};

    static {
        for (int i = 0; i < parts.length; i++) {
            Set<String> set = new HashSet<>(Arrays.asList(parts[i]));
            String[] newPart = new String[set.size()];
            set.toArray(newPart);
            parts[i] = newPart;
        }
    }

    public String generate() {
        StringBuilder res = new StringBuilder();
        for (String[] part : parts) {
            res.append(part[Math.abs(random.nextInt(part.length)) % part.length]).append(" ");
        }
        return res.toString().trim();
    }
}
