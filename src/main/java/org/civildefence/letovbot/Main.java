package org.civildefence.letovbot;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        //BasicConfigurator.configure();

        try {
            Logger logger = Logger.getRootLogger();
            Properties p = new Properties();
            ClassLoader classLoader = new Main().getClass().getClassLoader();
            File file = new File(classLoader.getResource("log4j.properties").getFile());
            p.load(new FileInputStream(file));
            PropertyConfigurator.configure(p);
            logger.info("Wow! I'm configured!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new LetovBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
