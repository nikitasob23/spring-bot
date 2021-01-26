package com.bot.springbot.server;

import com.bot.springbot.BotConfig;

public class ServerConfig {
    private String methodUrlTemplate;
    private String sendMessageMethod;
    private String token;
    private String currenciesFileName;
    private String currenciesSourceUrl;
    private String serverMessagesFileName;
    private long currenciesUpdateFrequencyInMillis;

    public ServerConfig(BotConfig botConfig) {
        this.methodUrlTemplate = botConfig.getServerMethodUrlTemplate();
        this.sendMessageMethod = botConfig.getSendMessageMethod();
        this.token = botConfig.getToken();
        this.currenciesFileName = botConfig.getCurrenciesFileName();
        this.currenciesSourceUrl = botConfig.getCurrenciesSourceUrl();
        this.serverMessagesFileName = botConfig.getServerMessagesFileName();
        this.currenciesUpdateFrequencyInMillis = botConfig.getCurrenciesUpdateFrequencyInMillis();
    }

    public String getMethodUrlTemplate() {
        return methodUrlTemplate;
    }

    public void setMethodUrlTemplate(String methodUrlTemplate) {
        this.methodUrlTemplate = methodUrlTemplate;
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
