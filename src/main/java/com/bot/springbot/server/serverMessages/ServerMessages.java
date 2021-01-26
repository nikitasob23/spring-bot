package com.bot.springbot.server.serverMessages;

import com.fasterxml.jackson.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties({"logger"})
public class ServerMessages {

    private final Logger logger = LoggerFactory.getLogger(ServerMessages.class);

    private String help;

    private String unidentified;

    public ServerMessages() {
    }

    public ServerMessages(ServerMessagesBuilder builder) {
        this.help = builder.getHelp();

        this.unidentified = builder.getUnidentified();
    }

    public Logger getLogger() {
        return logger;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getUnidentified() {
        return unidentified;
    }

    public void setUnidentified(String unidentified) {
        this.unidentified = unidentified;
    }

    @Override
    public String toString() {
        return "ServerMessagesText{" +
                ", welcome='" + help + '\'' +
                ", unidentified='" + unidentified + '\'' +
                '}';
    }
}
