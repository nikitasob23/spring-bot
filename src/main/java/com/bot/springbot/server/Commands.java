package com.bot.springbot.server;

public enum Commands {
    HELP,
    ALL,
    UNKNOWN;

    public static Commands toCommandType(String str) {
        str = str.toUpperCase();
        for (Commands command : Commands.values()) {
            if (str.equals(command.toString())) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
