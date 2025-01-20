package com.karen.karen_mqtt_integration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karen.karen_mqtt_integration.dto.MQTTMessageDTO;
import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.entity.Request;
import com.karen.karen_mqtt_integration.entity.Sensor;
import com.karen.karen_mqtt_integration.entity.SensorData;
import org.eclipse.paho.client.mqttv3.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MQTTService {

    private final DeviceService deviceService;
    private final SensorService sensorService;
    private final RequestService requestService;
    private final SensorDataService sensorDataService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${mqtt.broker.url}")
    private String brokerUrl = "tcp://192.168.50.166:1883";

    @Value("${mqtt.topic}")
    private String topic = "test/esp32/demo";

    public MQTTService(DeviceService deviceService, SensorService sensorService, RequestService requestService, SensorDataService sensorDataService) {
        System.out.println("MQTT Broker URL: " + brokerUrl);
        System.out.println("MQTT Topic: " + topic);
        this.deviceService = deviceService;
        this.sensorService = sensorService;
        this.requestService = requestService;
        this.sensorDataService = sensorDataService;
        System.out.println("MQTTService created");
        connectAndSubscribe();
    }

    public void connectAndSubscribe() {
        try {
            System.out.println("Connecting to MQTT broker: " + brokerUrl);
            MqttClient client = new MqttClient(brokerUrl, MqttClient.generateClientId());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);

            client.subscribe(topic, (topic, message) -> {
                String payload = new String(message.getPayload());
                saveTemperatureData(payload);
            });

            System.out.println("Connected to MQTT broker and subscribed to topic: " + topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTemperatureData(String jsonMessage) {
        try {
            MQTTMessageDTO message = objectMapper.readValue(jsonMessage, MQTTMessageDTO.class);

            boolean isDeviceExist = deviceService.existByMacAddress(message.getMacAddress());

            Device device = null;
            if (isDeviceExist) {
                device = deviceService.findByMacAddress(message.getMacAddress());
                System.out.println("Device already exists: " + device.getMacAddress());
            } else {
                device = deviceService.saveDevice(Device.builder()
                        .macAddress(message.getMacAddress())
                        .build());
            }

            Optional<Sensor> existingSensor = sensorService.findByName(message.getSensor());

            Sensor sensor = null;
            if (existingSensor.isPresent()) {
                sensor = existingSensor.get();
                System.out.println("Sensor already exists: " + existingSensor.get().getName());
            } else {
                sensor = sensorService.saveSensor(device.getId(), Sensor.builder()
                        .name(message.getSensor())
                        .build()
                );
            }

            Request request = requestService.addRequest(device.getId(), sensor.getId(), Request.builder()
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
