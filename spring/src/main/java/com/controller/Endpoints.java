package com.controller;

import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Endpoints {

    String publisherId = UUID.randomUUID().toString();
    IMqttClient publisher = new MqttClient("tcp://192.168.1.3:1883", publisherId);
    MqttConnectOptions options = new MqttConnectOptions();
    String messagePins = "null";
    String messageBattery = "null";

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

    @RequestMapping("/readPins")
    public String readPins() {

        return messagePins;
    }

    @RequestMapping("/battery")
    public String battery() {

        return messageBattery;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String messageRecived;
                messageRecived = message.toString();

                if(messageRecived.contains("/readPins")) {
                    messagePins = (String)message.getPayload();
                }

                if(messageRecived.contains("/battery")) {
                    messageBattery = (String)message.getPayload();
                }
//                System.out.println(message.getPayload());
            }

        };
    }



}
