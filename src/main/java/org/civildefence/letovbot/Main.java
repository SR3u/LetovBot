package org.civildefence.letovbot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class Main {
    protected static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("bot.properties"));
        loadProperty("java.net.useSystemProxies", properties);
        if ("false".equalsIgnoreCase(System.getProperty("java.net.useSystemProxies", "true"))) {
            // SOCKS5
            loadProperty("socksProxyHost", properties);
            loadProperty("socksProxyPort", properties);
            loadProperty("java.net.socks.username", properties);
            loadProperty("java.net.socks.password", properties);
        }
        loadProperty("telegram.bot.token", properties);
        loadProperty("telegram.bot.username", properties);

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestorType() == RequestorType.SERVER && "SOCKS5".equalsIgnoreCase(getRequestingProtocol())) {
                    String user = System.getProperty("java.net.socks.username", "");
                    String password = System.getProperty("java.net.socks.password", "");
                    String host = System.getProperty("socksProxyHost", "");
                    String port = System.getProperty("socksProxyPort", "");
                    if (getRequestingHost().equalsIgnoreCase(host)) {
                        if (Integer.parseInt(port) == getRequestingPort()) {
                            logger.info("Authenticating proxy: " + host + ":" + port);
                            return new PasswordAuthentication(user, password.toCharArray());
                        }
                    }
                }
                return null;
            }
        });

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new LetovBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void loadProperty(String key, Properties properties) {
        if (!properties.containsKey(key)) {
            throw new RuntimeException("no '" + key + "' in bot.properties!");
        }
        System.setProperty(key, properties.getProperty(key));
    }
}
