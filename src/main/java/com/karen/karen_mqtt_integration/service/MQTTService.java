package com.karen.karen_mqtt_integration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karen.karen_mqtt_integration.configuration.MQTTConfig;
import com.karen.karen_mqtt_integration.dto.MQTTMessageDTO;
import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.entity.Request;
import com.karen.karen_mqtt_integration.entity.Sensor;
import com.karen.karen_mqtt_integration.entity.SensorData;
import org.eclipse.paho.client.mqttv3.*;

import org.springframework.stereotype.Service;

@Service
public class MQTTService {

    private final DeviceService deviceService;
    private final SensorService sensorService;

    private final RequestService requestService;
    private final SensorDataService sensorDataService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MQTTConfig mqttConfig;

    public MQTTService(DeviceService deviceService, SensorService sensorService, RequestService requestService, SensorDataService sensorDataService, MQTTConfig mqttConfig) {
        this.deviceService = deviceService;
        this.sensorService = sensorService;

        this.requestService = requestService;
        this.sensorDataService = sensorDataService;
        this.mqttConfig = mqttConfig;
        System.out.println("MQTTService created");
        connectAndSubscribe();
    }

    public void connectAndSubscribe() {
        try {
            System.out.println("Connecting to MQTT broker: " + mqttConfig.getUrl());
            MqttClient client = new MqttClient(mqttConfig.getUrl(), MqttClient.generateClientId());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);

            client.subscribe(mqttConfig.getTopic(), (topic, message) -> {
                String payload = new String(message.getPayload());
                saveTemperatureData(payload);
            });

            System.out.println("Connected to MQTT broker and subscribed to topic: " + mqttConfig.getTopic());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTemperatureData(String jsonMessage) {
        try {
            MQTTMessageDTO message = objectMapper.readValue(jsonMessage, MQTTMessageDTO.class);

            Device device = deviceService.findAndSaveDevice(message.getMacAddress());

            Sensor sensor = sensorService.findAndSaveSensor(device, message.getSensor());

            Request request = requestService.saveRequest(device.getId(), sensor.getId(), Request.builder()
                    .requestId(message.getRequestId())
                    .build());


            for (MQTTMessageDTO.SensorDataDTO dataDTO : message.getData()) {
                sensorDataService.saveSensorData(request.getId(), SensorData.builder()
                        .address(dataDTO.getAddress())
                        .value(dataDTO.getValue())
                        .build());
            }

            System.out.println("Saved new message form " + message.getMacAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
