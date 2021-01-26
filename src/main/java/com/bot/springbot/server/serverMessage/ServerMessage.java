package com.bot.springbot.server.serverMessage;

import com.bot.springbot.server.notification.Recipient;

public class ServerMessage {
    Recipient recipient;
    Message message;

    public ServerMessage() {
    }

    public ServerMessage(Recipient recipient, Message message) {
        this.recipient = recipient;
        this.message = message;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "recipient=" + recipient +
                ", message=" + message +
                '}';
    }
}
