package com.karen.karen_mqtt_integration;

import com.karen.karen_mqtt_integration.configuration.MQTTConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({MQTTConfig.class})
public class KarenMqttIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(KarenMqttIntegrationApplication.class, args);
	}

}
