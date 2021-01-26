package com.bot.springbot.client;

import com.bot.springbot.BotConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfigFactory {
    private final BotConfig botConfig;

    public ClientConfigFactory(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public ClientConfig createClientConfig() {
        return new ClientConfig(botConfig);
    }
}
