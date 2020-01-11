#include "messageRecived.h"

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

int read(String payload) {
  DynamicJsonDocument doc(1024);
  deserializeJson(doc, payload);
  
  int pin = doc["pin"];
  
  return digitalRead(pin);
}

void messageReceived(String &topic, String &payload) {
  
  Serial.println("message: " + topic + " - " + payload);

  if(topic == "/configuration") {
    configuration(payload);
  }

  if(topic == "/state") {
    state(payload);
  }

    if(topic == "/read") {
    read(payload);
  }


}

