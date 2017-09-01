package org.civildefence.letovbot.message_handlers.utils;


import java.util.*;

public class Dictionaries {
    private static Dictionary l33t = fillL33tDictionary();
    private static Dictionary cs = fillCsDictionary();
    private static Dictionary ru2en = fillRuToEnDictionary();

    public static Dictionary getLeet() {
        return getL33t();
    }

    private static Dictionary fillCsDictionary() {
        Dictionary res = new UnorderedDictionary();
        res.put("A", Arrays.asList("А"));
        res.put("Б", Arrays.asList("6"));
        res.put("В", Arrays.asList("B"));
        res.put("Г", Arrays.asList("r"));
        res.put("Д", Arrays.asList("D"));
        res.put("Е", Arrays.asList("Е"));
        res.put("Ж", Arrays.asList("\\}\\|\\{"));
        res.put("З", Arrays.asList("3"));
        res.put("И", Arrays.asList("u"));
        res.put("Й", Arrays.asList("ŭ "));
        res.put("К", Arrays.asList("K"));
        res.put("Л", Arrays.asList("JI", "\\/I"));
        res.put("М", Arrays.asList("M"));
        res.put("Н", Arrays.asList("H"));
        res.put("О", Arrays.asList("O"));
        res.put("П", Arrays.asList("II", "n", "\\/7"));
        res.put("P", Arrays.asList("P"));
        res.put("С", Arrays.asList("C"));
        res.put("Т", Arrays.asList("T", "m"));
        res.put("У", Arrays.asList("Y", "y"));
        res.put("Ф", Arrays.asList("cp", "\\(I\\)", "qp"));
        res.put("Х", Arrays.asList("X"));
        res.put("Ц", Arrays.asList("LL", "L\\|"));
        res.put("Ч", Arrays.asList("4\\)", "4\\|"));
        res.put("Ш", Arrays.asList("W", "LLI"));
        res.put("Щ", Arrays.asList("LLL", "LL\\|"));
        res.put("Ъ", Arrays.asList("\\`b"));
        res.put("Ы", Arrays.asList("bl"));
        res.put("Ь", Arrays.asList("b"));
        res.put("Э", Arrays.asList("\\-\\)"));
        res.put("Ю", Arrays.asList("IO", "10"));
        res.put("Я", Arrays.asList("9", "9I"));
        return res;
    }

    private static Dictionary fillL33tDictionary() {
        Dictionary map = new OrderedDictionary();
        map.put("Г", Arrays.asList("r"));
        map.put("Ж", Arrays.asList(">|<", "}|{", "]|["));
        map.put("И", Arrays.asList("|/|"));
        map.put("Й", Arrays.asList("|`/|"));
        map.put("Л", Arrays.asList("/\\", "J[", "J|", "JI", "J"));
        map.put("З", Arrays.asList("'/_"));
        map.put("П", Arrays.asList("||", "|^|", "/7"));
        map.put("Ф", Arrays.asList("<|>", "qp"));
        map.put("Ц", Arrays.asList("|_|_", "||_"));
        map.put("Ч", Arrays.asList("'-|", "4"));
        map.put("Ш", Arrays.asList("III", "w", "LLI"));
        map.put("Щ", Arrays.asList("LLL"));
        map.put("Ъ", Arrays.asList("'b"));
        map.put("Ы", Arrays.asList("bl", "b|"));
        map.put("Ь", Arrays.asList("b", "|o"));
        map.put("Э", Arrays.asList("€", "-)"));
        map.put("Ю", Arrays.asList("|-O", "10"));
        map.put("Я", Arrays.asList("9I", "9", "<|"));

        map.put("A", Arrays.asList("/-|", "4"));
        map.put("B", Arrays.asList("8"));
        map.put("C", Arrays.asList("(", "["));
        map.put("D", Arrays.asList("|) "));
        map.put("E", Arrays.asList("3"));
        map.put("F", Arrays.asList("|="));
        map.put("G", Arrays.asList("6 "));
        map.put("H", Arrays.asList("|-| "));
        map.put("I", Arrays.asList("|", "!", "1"));
        map.put("J", Arrays.asList(")"));
        map.put("K", Arrays.asList("|<", "|("));
        map.put("L", Arrays.asList("|_"));
        map.put("M", Arrays.asList("|\\/|", "/\\/\\"));
        map.put("N", Arrays.asList("|\\|", "/\\/"));
        map.put("O", Arrays.asList("0", "()"));
        map.put("P", Arrays.asList("|>", "Ы", "b|"));
        map.put("Q", Arrays.asList("9", "0"));
        map.put("R", Arrays.asList("|?", "|2"));
        map.put("S", Arrays.asList("5", "$"));
        map.put("T", Arrays.asList("7", "+"));
        map.put("U", Arrays.asList("|_|"));
        map.put("V", Arrays.asList("\\/"));
        map.put("W", Arrays.asList("\\/\\/", "\\X/"));
        map.put("X", Arrays.asList("*", "><"));
        map.put("Y", Arrays.asList("'/"));
        map.put("Z", Arrays.asList("2 "));

        Dictionary res = new OrderedDictionary();
        for (Map.Entry<String, List<String>> e : map.entrySet()) {
            List<String> newValue = new ArrayList<>();
            e.getValue().forEach(s -> {
                newValue.add(escape(s));
            });
            res.put(e.getKey(), newValue);
        }
        return res;
    }

    private static Dictionary fillRuToEnDictionary() {
        Dictionary res = new UnorderedDictionary();
        res.put("А", Collections.singletonList("A"));
        res.put("В", Collections.singletonList("B"));
        res.put("С", Collections.singletonList("C"));
        res.put("Е", Collections.singletonList("E"));
        res.put("Ё", Collections.singletonList("E"));
        res.put("Н", Collections.singletonList("H"));
        res.put("К", Collections.singletonList("K"));
        res.put("М", Collections.singletonList("M"));
        res.put("О", Collections.singletonList("O"));
        res.put("Р", Collections.singletonList("P"));
        res.put("Т", Collections.singletonList("T"));
        res.put("У", Collections.singletonList("Y"));
        res.put("Х", Collections.singletonList("X"));
        return res;
    }

    private static String escape(String s) {
        StringBuilder builder = new StringBuilder("");
        for (char achar : s.toCharArray()) {
            if (containsSpecialCharacter("" + achar)) {
                builder.append("\\");
            }
            builder.append(achar);
        }
        return (builder.toString());
    }

    public static Dictionary getCs() {
        return cs;
    }

    public static Dictionary getL33t() {
        return l33t;
    }

    public static Dictionary getRu2En() {
        return ru2en;
    }

    public static boolean containsSpecialCharacter(String s) {
        return (s == null) ? false : s.matches("[^A-Za-z0-9А-Яа-я ]");
    }

}
