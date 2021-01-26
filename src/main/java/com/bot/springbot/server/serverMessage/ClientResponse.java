package com.bot.springbot.server.serverMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientResponse {
    boolean success;

    @JsonProperty("recipient_id")
    String recipientId;

    @JsonProperty("message_id")
    String messageId;

    public boolean isSuccess() {
        return success;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", recipientId='" + recipientId + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
