#include <WiFi.h>
#include <MQTT.h>
#include "messageRecived.h"

const char ssid[] = "MQTT";
const char pass[] = "jDVH91u72PkM";

WiFiClient net;
MQTTClient client;

unsigned long lastMillis = 0;

void connect() {
  Serial.print("checking wifi...");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
  }

  Serial.print("\nconnecting...");
  while (!client.connect("arduino", "try", "try")) {
    Serial.print(".");
    delay(1000);
  }

  Serial.println("\nconnected!");

  client.subscribe("/configuration");
  client.subscribe("/state");
}

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, pass);

  client.begin("192.168.4.1", net);
  client.onMessage(messageReceived);

  connect();
}

void loop() {
  client.loop();
  delay(10);

  if (!client.connected()) {
    connect();
  }
  
  // pinMode(5, INPUT);
  
  // if (millis() - lastMillis > 1000) {
  //   lastMillis = millis();
  //   client.publish("/hello", (String)digitalRead(5));
  // }
}