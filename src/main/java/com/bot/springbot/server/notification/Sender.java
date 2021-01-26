package com.bot.springbot.server.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sender {
    @JsonProperty("user_id")
    String userId;

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
