package com.bot.springbot.server.notification;

public class Notification {
    private String webhookType;
    private Sender sender;
    private Recipient recipient;
    private Message message;
    private long timestamp;

    public String getWebhookType() {
        return webhookType;
    }

    public Sender getSender() {
        return sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Message getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "webhookType='" + webhookType + '\'' +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", message=" + message +
                ", timestamp=" + timestamp +
                '}';
    }
}
