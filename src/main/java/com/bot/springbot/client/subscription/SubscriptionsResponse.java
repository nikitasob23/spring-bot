package com.bot.springbot.client.subscription;

import java.util.Collections;
import java.util.List;

public class SubscriptionsResponse {
    private List<Subscription> subscriptions;

    public List<Subscription> getSubscriptions() {
        return Collections.unmodifiableList(subscriptions);
    }
}
