package com.bot.springbot.server.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipient {
    @JsonProperty("chat_id")
    private String chatId;

    public String getChatId() {
        return chatId;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "chatId='" + chatId + '\'' +
                '}';
    }
}
