package com.karen.karen_mqtt_integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mqtt")
public class MQTTConfig {
        private String url;
        private String topic;
}
