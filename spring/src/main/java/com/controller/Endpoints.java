package com.controller;

import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Endpoints {

    String publisherId = UUID.randomUUID().toString();
    IMqttClient publisher = new MqttClient("tcp://192.168.1.3:1883", publisherId);
    MqttConnectOptions options = new MqttConnectOptions();

    public Endpoints() throws MqttException {
    }

    @RequestMapping("/configuration")
    public void configuration(@RequestParam int pin, @RequestParam String mode) throws MqttException {
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);

        String json = new JSONObject()
                .put("pin", pin)
                .put("mode", mode).toString();

        byte[] msg = json.getBytes();

        publisher.publish("/configuration", new MqttMessage(msg));
        publisher.disconnect();
    }

    @RequestMapping("/state")
    public void state(@RequestParam int pin, @RequestParam String state) throws MqttException {
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);

        String json = new JSONObject()
                .put("pin", pin)
                .put("state", state).toString();

        byte[] msg = json.getBytes();

        publisher.publish("/state", new MqttMessage(msg));
        publisher.disconnect();
    }

}
