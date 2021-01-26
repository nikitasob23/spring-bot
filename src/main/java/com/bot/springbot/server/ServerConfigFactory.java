package com.bot.springbot.server;

import com.bot.springbot.BotConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfigFactory {
    private final BotConfig botConfig;

    public ServerConfigFactory(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public ServerConfig createServerConfig() {
        return new ServerConfig(botConfig);
    }
}
