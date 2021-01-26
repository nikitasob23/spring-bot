package com.bot.springbot.server;

import com.bot.springbot.server.calculator.CalcOperation;
import com.bot.springbot.server.calculator.Calculator;
import com.bot.springbot.server.currencyRate.Currencies;
import com.bot.springbot.server.currencyRate.CurrenciesBuilder;
import com.bot.springbot.server.notification.Notification;
import com.bot.springbot.server.notification.Recipient;
import com.bot.springbot.server.serverMessage.Message;
import com.bot.springbot.server.serverMessage.ClientResponse;
import com.bot.springbot.server.serverMessage.ServerMessage;
import com.bot.springbot.server.serverMessages.ServerMessages;
import com.bot.springbot.server.serverMessages.ServerMessagesBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final RestTemplate template;

    private final ServerConfig config;

    private final ServerMessages serverMessages;

    private Currencies currencies;

    private final String valuteIdBorder = " В ";

    public Server(RestTemplate template, ServerConfig config) {
        this.template = template;
        this.config = config;
        this.serverMessages = new ServerMessagesBuilder().build(config.getServerMessagesFileName());
        this.currencies = new CurrenciesBuilder()
                .setSourceFileName(config.getCurrenciesFileName())
                .setSourceUrl(config.getCurrenciesSourceUrl())
                .setRequestTimeMillis(System.currentTimeMillis())
                .build(template);
    }

    @PostMapping("/bothook")
    @ResponseBody
    public void serveHook(@RequestBody Notification notification) {
        currencies = currencies.compareRequestTimeAndUpdate(
                template,
                System.currentTimeMillis(),
                config.getCurrenciesUpdateFrequencyInMillis()
        );

        logger.info("Got the message " + notification);
        Recipient recipient = notification.getRecipient();
        String clientText = notification.getMessage().getText().toUpperCase();
        switch (Commands.toCommandType(clientText)) {
            case HELP -> sendMessage(recipient, serverMessages.getHelp());
            case ALL -> sendMessage(
                    recipient,
                    currencies.getValute().values().stream()
                            .map(currency -> currency.getName() + ": " + currency.getValue() + "\n")
                            .reduce((c1, c2) -> c1 + c2)
                            .stream().collect(Collectors.joining())
            );
            default -> {
                String currencyValue = CalcOperation.contains(clientText)
                        ? calculateValue(clientText) : convertValue(clientText);
                sendMessage(
                        recipient,
                        currencyValue
                );
            }
        }
    }

    private String calculateValue(String clientText) {
        String[] splitClientText = splitClientText(clientText);

        String valuteId = splitClientText[0];

        try {
            String expression = splitClientText[1];
            String resultValuteId = splitClientText[2];

            Calculator calculator = new Calculator(expression);
            if (calculator.getResult() < 0) {
                throw new IllegalArgumentException();
            }
            return currencies.getCurrency(resultValuteId).getName() + ": " + new DecimalFormat("#0.00")
                    .format(currencies.parseValue( calculator.getResult(), valuteId, resultValuteId));
        } catch (Exception e) {
            logger.info("Failed calculate to another currency. Exception message: " + e.getMessage());
            return serverMessages.getUnidentified();
        }

    }

    private String[] splitClientText(String clientText) {
        AtomicInteger numOfCharDigits = new AtomicInteger();
        String expression = getExpression(clientText, numOfCharDigits);
        int endValuteId = clientText.indexOf(valuteIdBorder);
        String valuteId = clientText.substring(numOfCharDigits.get(), endValuteId);
        String resultValuteId = clientText.substring(endValuteId + 3);

        return new String[] { valuteId, expression, resultValuteId };
    }

    private String getExpression(String clientText, AtomicInteger numOfCharDigits) {
        numOfCharDigits.setRelease(-1);
        clientText.chars()
                .mapToObj(num -> (char) num)
                .peek(i -> numOfCharDigits.incrementAndGet())
                .allMatch(ch -> Calculator.isPartOfDigit(ch) || CalcOperation.contains(ch) || ch.equals(' '));
        if (numOfCharDigits.compareAndSet(0, 0)) {
            return clientText;
        }
        return clientText.substring(0, numOfCharDigits.get())
                .replace(" ", "");
    }

    private String convertValue(String clientText) {
        String[] split = clientText.split(valuteIdBorder);
        String expression = Calculator.haveDigit(clientText) ? getExpression(split[0], new AtomicInteger()) : "";
        try {
            String valuteId = split[0]
                    .replace(expression, "");
            valuteId = valuteId.charAt(0) == ' ' ? valuteId.substring(1) : valuteId;
            String resultValuteId = split[1];
            double value = currencies.parseValue(
                    expression.isEmpty() ? 1d : Double.parseDouble(expression),
                    valuteId,
                    resultValuteId
            );
            logger.info("Success search for the currency");
            return currencies.getCurrency(resultValuteId).getName() + ": " + new DecimalFormat("#0.00")
                    .format(value);
        } catch (Exception e) {
            logger.info("Failed convert for the currency. Exception message: " + e.getMessage());
            return serverMessages.getUnidentified();
        }
    }

    private void sendMessage(Recipient recipient, String text) {
        logger.info("Trying to send message...");
        ServerMessage message = new ServerMessage(recipient, new Message(text));
        ClientResponse response = template.postForObject(
                String.format(config.getMethodUrlTemplate(), config.getSendMessageMethod(), recipient.getChatId(),
                        config.getToken()),
                message,
                ClientResponse.class
        );
        if (response != null && response.isSuccess()) {
            logger.info("Send message " + message);
            logger.info("Got the response " + response);
        } else {
            logger.info("The message was not sent");
        }
    }

}
