#include <WiFi.h>
#include <MQTT.h>
#include <ArduinoJson.h>

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

void configuration(String payload) {

  DynamicJsonDocument doc(1024);
  deserializeJson(doc, payload);
  
  int pin = doc["pin"];
  String mode = doc["mode"];
  
  if(mode == "OUTPUT") {
    pinMode(pin, OUTPUT);
  } else if(mode == "INPUT"){
    pinMode(pin, INPUT);
  }
}

void state(String payload) {

  DynamicJsonDocument doc(1024);
  deserializeJson(doc, payload);
  
  int pin = doc["pin"];
  String state = doc["state"];
  
  if(state == "HIGH") {
    digitalWrite(pin, HIGH);
  } else if (state == "LOW"){
    digitalWrite(pin, LOW);
  }
}

void read() {
  DynamicJsonDocument response(1024);
  String toSend;

  response["pin"] = 5;
  response["state"] = digitalRead(5);
  serializeJson(response, toSend);

  client.publish("/readPins", toSend);
}

void readBattery() {
  long battery = random(3000, 4000);
  client.publish("/battery", (String)battery);
}

void messageReceived(String &topic, String &payload) {
  
  Serial.println("message: " + topic + " - " + payload + "\n");

  if(topic == "/configuration") {
    configuration(payload);
  }

  if(topic == "/state") {
    state(payload);
  }

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

  if (millis() - lastMillis > 3000) {
    lastMillis = millis();
    read();
    readBattery();
  }
  
}