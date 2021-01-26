package com.bot.springbot.server.serverMessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@JsonIgnoreProperties("logger")
public class ServerMessagesBuilder {

    private Logger logger = LoggerFactory.getLogger(ServerMessagesBuilder.class);

    private String help;

    private String unidentified;

    public Logger getLogger() {
        return logger;
    }

    public ServerMessagesBuilder setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public String getHelp() {
        return help;
    }

    public ServerMessagesBuilder setHelp(String help) {
        this.help = help;
        return this;
    }

    public String getUnidentified() {
        return unidentified;
    }

    public ServerMessagesBuilder setUnidentified(String unidentified) {
        this.unidentified = unidentified;
        return this;
    }

    public ServerMessages build(String sourceFileName) {
        Path sourceFilePath = Paths.get(sourceFileName);
        if (Files.exists(sourceFilePath)) {
            return getServerMessagesFromSourceFile(sourceFilePath);
        }
        logger.info("Source file with server messages {} was not found", sourceFileName);
        return null;
    }

    private ServerMessages getServerMessagesFromSourceFile(Path sourceFilePath) {
        ServerMessagesBuilder builder;
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(sourceFilePath))) {
            builder = new ObjectMapper().readValue(reader, ServerMessagesBuilder.class);
            logger.info("Successful reading of server messages from a file {}. Got server messages {}", sourceFilePath.toString(), builder);
        } catch (IOException e) {
            logger.info("Failed reading of server messages from a file {}. Exception message {}", sourceFilePath.toString(), e.getMessage());
            return null;
        }
        return new ServerMessages(builder);
    }

    @Override
    public String toString() {
        return "ServerMessagesBuilder{" +
                "help='" + help + '\'' +
                ", unidentified='" + unidentified + '\'' +
                '}';
    }
}
