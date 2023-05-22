package com.messenger.notification.messagebroker.config.props;

import lombok.Data;

@Data
public class ExchangeProp {
    private String name;
    private boolean durable = true;
    private boolean autoDelete = false;
}
