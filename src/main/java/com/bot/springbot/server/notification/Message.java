package com.bot.springbot.server.notification;

public class Message {
    private String text;
    private long seq;
    private String mid;

    public String getText() {
        return text;
    }

    public long getSeq() {
        return seq;
    }

    public String getMid() {
        return mid;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", seq=" + seq +
                ", mid='" + mid + '\'' +
                '}';
    }
}
