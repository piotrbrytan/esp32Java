#include "messageRecived.h"

void messageReceived(String &topic, String &payload) {
  Serial.println("incoming message: " + topic + " - " + payload);
}