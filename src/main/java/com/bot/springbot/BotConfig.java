package com.bot.springbot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String botHook;
    private String clientMethodUrlTemplate;
    private String serverMethodUrlTemplate;
    private String subscribeMethod;
    private String subscriptionsMethod;
    private String sendMessageMethod;
    private String token;
    private String currenciesFileName;
    private String currenciesSourceUrl;
    private String serverMessagesFileName;
    private long currenciesUpdateFrequencyInMillis;

    public String getBotHook() {
        return botHook;
    }

    public void setBotHook(String botHook) {
        this.botHook = botHook;
    }

    public String getClientMethodUrlTemplate() {
        return clientMethodUrlTemplate;
    }

    public void setClientMethodUrlTemplate(String clientMethodUrlTemplate) {
        this.clientMethodUrlTemplate = clientMethodUrlTemplate;
    }

    public String getServerMethodUrlTemplate() {
        return serverMethodUrlTemplate;
    }

    public void setServerMethodUrlTemplate(String serverMethodUrlTemplate) {
        this.serverMethodUrlTemplate = serverMethodUrlTemplate;
    }

    public String getSubscribeMethod() {
        return subscribeMethod;
    }

    public void setSubscribeMethod(String subscribeMethod) {
        this.subscribeMethod = subscribeMethod;
    }

    public String getSubscriptionsMethod() {
        return subscriptionsMethod;
    }

    public void setSubscriptionsMethod(String subscriptionsMethod) {
        this.subscriptionsMethod = subscriptionsMethod;
    }

    public String getSendMessageMethod() {
        return sendMessageMethod;
    }

    public void setSendMessageMethod(String sendMessageMethod) {
        this.sendMessageMethod = sendMessageMethod;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCurrenciesFileName() {
        return currenciesFileName;
    }

    public void setCurrenciesFileName(String currenciesFileName) {
        this.currenciesFileName = currenciesFileName;
    }

    public String getCurrenciesSourceUrl() {
        return currenciesSourceUrl;
    }

    public void setCurrenciesSourceUrl(String currenciesResponseUrl) {
        this.currenciesSourceUrl = currenciesResponseUrl;
    }

    public String getServerMessagesFileName() {
        return serverMessagesFileName;
    }

    public void setServerMessagesFileName(String serverMessagesFileName) {
        this.serverMessagesFileName = serverMessagesFileName;
    }

    public long getCurrenciesUpdateFrequencyInMillis() {
        return currenciesUpdateFrequencyInMillis;
    }

    public void setCurrenciesUpdateFrequencyInMillis(long currenciesUpdateFrequencyInMillis) {
        this.currenciesUpdateFrequencyInMillis = currenciesUpdateFrequencyInMillis;
    }
}
