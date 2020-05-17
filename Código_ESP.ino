#include <PubSubClient.h>
#include <HTTPClient.h>
#include "ArduinoJson.h"
#include <WiFi.h>

char responseBuffer[300];
WiFiClient client;

char SSID[] = "MiFibra-C416";
char PASS[] = "CHAMGONZ";

String SERVER_IP = "192.168.1.137";
int SERVER_PORT = 8080;

const char* mqtt_server = "192.168.1.137";
PubSubClient MQTTClient(client);

int device = 28;
int LocationSensor;
int PressureSensor;
int SoundSensor;
int DistanceSensor;
int LedActuator;
int VibrationActuator;

void setup() {
  Serial.begin(9600);
  Serial.println("Dispositivo Iniciado");
  WiFi.begin(SSID, PASS);
  
  Serial.println("Connecting...");
  while (WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.print(".");
  }
  Serial.print("Connected, IP address: ");
  Serial.println(WiFi.localIP());
  
  MQTTClient.setServer(mqtt_server, 1885);
  MQTTClient.setCallback(callback);

  getSensorsOfDevice();
  getActuatorsOfDevice();
}

void loop(){
  //if (!client.connected()) {
  //  MQTTReconnect();
  //}
  postLocation();
  postPressure();
  postSound();
  postDistance();
  delay(5000);
}

void callback(char* topic, byte* message, unsigned int length) {
  Serial.print("Message arrived on topic: ");
  Serial.print(topic);
  Serial.print(". Message: ");
  String messageTemp;
  
  for (int i = 0; i < length; i++) {
    Serial.print((char)message[i]);
    messageTemp += (char)message[i];
  }
  Serial.println();
  if (String(topic) == "led") {
    Serial.print("Changing output to ");
    if(messageTemp == "on"){
      Serial.println("on");
    }
    else Serial.println("no se que devuelve el servidor");
  }
}


void MQTTReconnect() {
  while (!MQTTClient.connected()) {
    Serial.print("Attempting MQTT connection...");
    if (MQTTClient.connect("ESP32Client-28")) {
      Serial.println("connected");
      MQTTClient.subscribe("led");
    } else {
      Serial.print("failed, rc=");
      Serial.print(MQTTClient.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}

void getSensorsOfDevice(){
  if (WiFi.status() == WL_CONNECTED){
      HTTPClient http;
      http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensorsOf/" + String(device, DEC), true);
      
      if(http.GET() == 200){
      String payload = http.getString();
      
      const size_t capacity = JSON_OBJECT_SIZE(16) + JSON_ARRAY_SIZE(16) + 60;
      DynamicJsonDocument doc(capacity);
      deserializeJson(doc, payload);
      
      JsonArray array = doc.as<JsonArray>();
      for(JsonObject v : array) {
        String nombre = v["name"].as<String>();
        if(nombre == "Location"){
          LocationSensor = v["idsensor"].as<int>();
        }else if(nombre == "Pressure"){
          PressureSensor = v["idsensor"].as<int>();
        }else if(nombre == "Sound"){
          SoundSensor = v["idsensor"].as<int>();
        }else if(nombre == "Distance"){
          DistanceSensor = v["idsensor"].as<int>();
        }
        /*switch(nombre){
          case "Location": LocationSensor = v["idsensor"].as<int>(); break;
          case "Pressure": PressureSensor = v["idsensor"].as<int>(); break;
          case "Sound": SoundSensor = v["idsensor"].as<int>(); break;
          case "Distance": DistanceSensor = v["idsensor"].as<int>(); break;
          default: Serial.println("Error al decodificar id de sensor");
        }*/
      }
      Serial.print("ID's de los Sensores: ");
      Serial.print(String(LocationSensor, DEC) + ",");
      Serial.print(String(PressureSensor, DEC) + ",");
      Serial.print(String(SoundSensor, DEC) + ",");
      Serial.println(String(DistanceSensor, DEC) + ".");
      
    }else Serial.println("Error al conectar con el servidor");
    
  }else Serial.println("Error al conectar mediante WIFI");
}

void getActuatorsOfDevice(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/actuatorsOf/" + String(device, DEC), true);
    if(http.GET() == 200){
      String payload = http.getString();
      
      const size_t capacity = JSON_OBJECT_SIZE(8) + JSON_ARRAY_SIZE(8) + 60;
      DynamicJsonDocument doc(capacity);
      deserializeJson(doc, payload);
      
      JsonArray array = doc.as<JsonArray>();
      for(JsonObject v : array) {
        String nombre = v["name"].as<String>();
        if(nombre == "led"){
          LedActuator = v["idactuator"].as<int>();
        }else if(nombre == "vibration"){
          VibrationActuator = v["idactuator"].as<int>();
        }
      }
      Serial.print("ID's de los Actuadores: ");
      Serial.print(String(LedActuator, DEC) + ",");
      Serial.println(String(VibrationActuator, DEC) + ".");
      
    }else Serial.println("Error al conectar con el servidor");
    
  }else Serial.println("Error al conectar mediante WIFI");
}

void postLocation(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(LocationSensor, DEC), true);
    String value = "{\"id\": 0,\"x\": 657.0,\"y\": 456.0,\"timestamp\": 1}";
    int code = http.POST(value);
    String payload = http.getString();
    if(code == 200){
      Serial.println("Location Posted Correctly");
    
    }else Serial.println("Error al conectar con el servidor:" + String(code,DEC));
    //Serial.println("Respuesta: ");
    //Serial.println(payload);
  }else Serial.println("Error al conectar mediante WIFI");
}

void postPressure(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(PressureSensor, DEC), true);
    String value = "{\"id\": 0,\"value\": 345.0,\"timestamp\": 1}";
    int code = http.POST(value);
    String payload = http.getString();
    if(code == 200){
      Serial.println("Pressure Posted Correctly");
    
    }else Serial.println("Error al conectar con el servidor:" + String(code,DEC));
    //Serial.println("Respuesta:");
    //Serial.println(payload);
  }else Serial.println("Error al conectar mediante WIFI");
}

void postSound(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(SoundSensor, DEC), true);
    String value = "{\"id\": 0,\"decibels\": 345.0,\"timestamp\": 1}";
    int code = http.POST(value);
    String payload = http.getString();
    if(code == 200){
      Serial.println("Sound Posted Correctly");
    
    }else Serial.println("Error al conectar con el servidor:" + String(code,DEC));
    //Serial.println("Respuesta:");
    //Serial.println(payload);
  }else Serial.println("Error al conectar mediante WIFI");
}

void postDistance(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(DistanceSensor, DEC), true);
    String value = "{\"id\": 0,\"distance_to_door\": 523.0,\"is_inside\": true,\"timestamp\": 1,\"is_near_door\": false}";
    int code = http.POST(value);
    String payload = http.getString();
    if(code == 200){
      Serial.println("Distance Posted Correctly");
    
    }else Serial.println("Error al conectar con el servidor:" + String(code,DEC));
    //Serial.println("Respuesta:");
    //Serial.println(payload);
  }else Serial.println("Error al conectar mediante WIFI");
}








      
