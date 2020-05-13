#include <Arduino.h>
#include "ArduinoJson.h"
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <SoftwareSerial.h>

char responseBuffer[300];
WiFiClient client;

String SSID = "MiFibra";
String PASS = "CHAMGONZ";

String SERVER_IP = "192.168.1.137";
int SERVER_PORT = 8080;

int device = 28;
int LocationSensor = 72;
int PressureSensor = 73;
int SoundSensor = 74;
int DistanceSensor = 75;

void sendGetRequestSensorsOfDevice();
void sendPostRequestLocation();
void sendPostRequestPressureSound(int sensorID);
void sendPostRequestDistance();

void setup() {
  Serial.begin(9600);

  WiFi.begin(SSID, PASS);

  Serial.print("Connecting...");
  while (WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.print(".");
  }
  Serial.print("Connected, IP address: ");
  Serial.print(WiFi.localIP());

  sendGetRequestSensorsOfDevice();
}

void loop(){
  sendPostRequestLocation();
  sendPostRequestPressureSound(PressureSensor);
  sendPostRequestPressureSound(SoundSensor);
  sendPostRequestDistance();
  delay(3000);
}

void sendPostRequestLocation(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(LocationSensor), true);
    http.addHeader("Content-Type", "application/json");

    const size_t capacity = JSON_OBJECT_SIZE(4) + JSON_ARRAY_SIZE(2) + 60;
    DynamicJsonDocument doc(capacity);
    doc["x"] = 18;
    doc["y"] = 78;
    doc["timestamp"] = 124123123;

    String output;
    serializeJson(doc, output);

    int httpCode = http.PUT(output);

    Serial.println("Response code: " + httpCode);

    String payload = http.getString();

    Serial.println("Resultado: " + payload);
  }
}

void sendPostRequestPressureSound(int sensorID){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(sensorID), true);
    http.addHeader("Content-Type", "application/json");

    const size_t capacity = JSON_OBJECT_SIZE(3) + JSON_ARRAY_SIZE(2) + 60;
    DynamicJsonDocument doc(capacity);
    doc["value"] = 18;
    doc["timestamp"] = 124123123;

    String output;
    serializeJson(doc, output);

    int httpCode = http.PUT(output);

    Serial.println("Response code: " + httpCode);

    String payload = http.getString();

    Serial.println("Resultado: " + payload);
  }
}

void sendPostRequestDistance(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(DistanceSensor), true);
    http.addHeader("Content-Type", "application/json");

    const size_t capacity = JSON_OBJECT_SIZE(4) + JSON_ARRAY_SIZE(2) + 60;
    DynamicJsonDocument doc(capacity);
    doc["distance_to_door"] = 18;
    doc["is_inside"] = 0;
    doc["timestamp"] = 124123123;

    String output;
    serializeJson(doc, output);

    int httpCode = http.PUT(output);

    Serial.println("Response code: " + httpCode);

    String payload = http.getString();

    Serial.println("Resultado: " + payload);
  }
}

void sendGetRequestSensorsOfDevice(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensorsOf/" + String(device), true);
    int httpCode = http.GET();

    Serial.println("Response code: " + httpCode);

    String payload = http.getString();

    const size_t capacity = JSON_OBJECT_SIZE(4) + JSON_ARRAY_SIZE(5) + 60;
    DynamicJsonDocument doc(capacity);

    DeserializationError error = deserializeJson(doc, payload);
    if (error){
      Serial.print("deserializeJson() failed: ");
      Serial.println(error.c_str());
      return;
    }

    Serial.println(F("Response:"));
    int iddevice, idsensor;
    String name;
    for(int i=0;i<4;i++){
      iddevice = doc[i]["iddevice"].as<int>();
      idsensor = doc[i]["idsensor"].as<int>();
      name = doc[i]["name"].as<char*>();

      if(name == "Location"){
        LocationSensor = idsensor;
      }else if(name == "Pressure"){
        PressureSensor = idsensor;
      }else if(name == "Sound"){
        SoundSensor = idsensor;
      }else if(name == "Distance"){
        DistanceSensor = idsensor;
      }

      Serial.println("Id Device: " + String(iddevice));
      Serial.println("Id sensor: " + String(idsensor));
      Serial.println("Nombre del sensor: " + name);
    }
  }
}
