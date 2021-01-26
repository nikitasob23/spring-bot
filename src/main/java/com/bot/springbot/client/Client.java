package com.bot.springbot.client;

import com.bot.springbot.client.subscribe.SubscribeBody;
import com.bot.springbot.client.subscribe.SubscribeResponse;
import com.bot.springbot.client.subscription.Subscription;
import com.bot.springbot.client.subscription.SubscriptionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private final RestTemplate template;

    private final ClientConfig config;

    public Client(RestTemplate template, ClientConfig config) {
        this.template = template;
        this.config = config;
    }

    @PostConstruct
    void init() {
        if (!checkSubscriptions() && !subscribe()) {
            logger.error("Failed to subscribe. Exiting...");
            System.exit(1);
        }
    }

    private boolean subscribe() {
        logger.info("Trying to subscribe...");
        final SubscribeResponse response = template.postForObject(
                String.format(config.getMethodUrlTemplate(), config.getSubscribeMethod(), config.getToken()),
                new SubscribeBody(config.getBotHook()),
                SubscribeResponse.class
        );
        boolean subscribeIsSuccess = response != null && response.isSuccess();
        if (subscribeIsSuccess) {
            logger.info("Success subscribe");
        }
        return subscribeIsSuccess;
    }

    public boolean checkSubscriptions() {
        logger.info("Checking for subscriptions...");
        final SubscriptionsResponse response = template.getForObject(
                String.format(config.getMethodUrlTemplate(), config.getSubscriptionsMethod(), config.getToken()),
                SubscriptionsResponse.class
        );
        if (response == null || response.getSubscriptions().isEmpty()) {
            logger.info("Subscriptions response is empty");
            return false;
        }
        final String botHook = config.getBotHook();
        boolean haveSubscription = response.getSubscriptions().stream()
                .anyMatch(sub -> botHook.equals(sub.getUrl()));

        if (haveSubscription) {
            logger.info("There is a subscription for " + botHook);
        } else {
            logger.info("There is no subscription for " + botHook);
        }
        return haveSubscription;
    }

    public void unsubscribeAll() {
        final SubscriptionsResponse subscriptionsResponse = template.getForObject(
                String.format(config.getMethodUrlTemplate(), config.getSubscriptionsMethod(), config.getToken()),
                SubscriptionsResponse.class
        );
        subscriptionsResponse.getSubscriptions()
                .forEach(sub -> template.postForObject(
                        "https://api.ok.ru/graph/me/unsubscribe?access_token=" + config.getToken(),
                        new SubscribeBody(sub.getUrl()),
                        SubscribeResponse.class
                ));
    }
}
