package com.bot.springbot.client.subscribe;

public class SubscribeBody {
    String url;

    public SubscribeBody(String url) {
        this.url = url;
    }

    public String getUrl() {
        return new String(url);
    }
}
