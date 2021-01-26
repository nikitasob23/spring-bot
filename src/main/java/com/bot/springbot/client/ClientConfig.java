package com.bot.springbot.client;

import com.bot.springbot.BotConfig;

public class ClientConfig {
    private final String botHook;
    private final String methodUrlTemplate;
    private final String subscribeMethod;
    private final String subscriptionsMethod;
    private final String token;

    public ClientConfig(BotConfig botConfig) {
        this.botHook = botConfig.getBotHook();
        this.methodUrlTemplate = botConfig.getClientMethodUrlTemplate();
        this.subscribeMethod = botConfig.getSubscribeMethod();
        this.subscriptionsMethod = botConfig.getSubscriptionsMethod();
        this.token = botConfig.getToken();
    }

    public String getBotHook() {
        return botHook;
    }

    public String getMethodUrlTemplate() {
        return methodUrlTemplate;
    }

    public String getSubscribeMethod() {
        return subscribeMethod;
    }

    public String getSubscriptionsMethod() {
        return subscriptionsMethod;
    }

    public String getToken() {
        return token;
    }
}
