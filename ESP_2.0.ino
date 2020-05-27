#include <SoftwareSerial.h>
#include <TinyGPS.h>
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

long lastMsg = 0;
char msg[50];
int value = 0;

int device = 28, LocationSensor, PressureSensor, SoundSensor, DistanceSensor, LedActuator, VibrationActuator;

const int freq = 5000, ledChannel = 0, ledChannel2 = 1, resolution = 8, ledPin = 26, vibPin = 27;
unsigned long tiempo=0, tiempo2=0;
bool estadoLED = true, estadoVIB = true;
unsigned int lum = 10, len = 1000, inten = 10, leng = 1000;

TinyGPS gps;
SoftwareSerial softSerial(1, 3);
float coor[2];

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

  pinMode(32,INPUT);
  pinMode(33,INPUT);
  ledcSetup(ledChannel, freq, resolution);
  ledcAttachPin(ledPin, ledChannel);
  ledcSetup(ledChannel2, freq, resolution);
  ledcAttachPin(vibPin, ledChannel2);
  //escribeLED();
  //escribeVIB();
  
  softSerial.begin(9600);
}

void loop(){
  if (!MQTTClient.connected()) {
    MQTTReconnect();
  }
  MQTTClient.loop();
  /*postLocation();
  postPressure();
  postSound();
  postDistance();*/
  
  escribeLED();
  escribeVIB();
  
}

void escribeLED(){
  if(len > 0){
    if(millis() > tiempo){
      if(estadoLED){
        ledcWrite(ledChannel, 0);
        estadoLED = false;
      }else{
        ledcWrite(ledChannel, lum);
        estadoLED = true;
      }
      tiempo += len;
    }
  }else ledcWrite(ledChannel, lum);
}

void escribeVIB(){
  if(leng > 0){
    if(millis() > tiempo2){
      if(estadoVIB){
        ledcWrite(ledChannel2, 0);
        estadoVIB = false;
      }else{
        ledcWrite(ledChannel2, inten);
        estadoVIB = true;
      }
      tiempo2 = millis() + leng;
    }
  }else ledcWrite(ledChannel2, inten);
}

float leeSonido(){
  int res=0;
  for(int i=0; i<2000; i++){
    res+= abs(2048 - analogRead(32));
  }
  res/=2000;
  res*=res;
  res/=100;
  return res;
}

void leeGPS(){
   bool newData = false;
   unsigned long chars;
   unsigned short sentences, failed;
   
   // Intentar recibir secuencia durante un segundo
   for (unsigned long start = millis(); millis() - start < 1000;)
   {
      while (softSerial.available())
      {
         char c = softSerial.read();
         if (gps.encode(c)) // Nueva secuencia recibida
            newData = true;
      }
   }
 
   if (newData)
   {
      unsigned long age;
      gps.f_get_position(&coor[0], &coor[1], &age);

   }
}

void callback(char* topic, byte* message, unsigned int len) {
  String messageTemp;
  const size_t capacity = JSON_OBJECT_SIZE(8) + JSON_ARRAY_SIZE(8) + 60;
  DynamicJsonDocument doc(capacity);
      
  for (int i = 0; i < len; i++) {
    messageTemp += (char)message[i];
  }

  Serial.println(messageTemp);
  
    deserializeJson(doc, messageTemp);
    JsonObject object = doc.as<JsonObject>();
    
    if(String(topic) == "led"){
      len = object["length"].as<int>();
      lum = object["luminosity"].as<int>();
      
    }else if(String(topic) == "vibration"){
      leng = object["length"].as<int>();
      inten = object["intensity"].as<int>();
    }
    Serial.print("len: ");
    Serial.print(len);
    Serial.print(", lum: ");
    Serial.print(lum);
    Serial.print("; leng: ");
    Serial.print(leng);
    Serial.print(", inten: ");
    Serial.print(inten);
    Serial.print("; tiempo: ");
    Serial.println(tiempo);
       
}


void MQTTReconnect() {
  while (!MQTTClient.connected()) {
    Serial.print("Attempting MQTT connection...");
    if (MQTTClient.connect("ESP32Client-28", "mqttbroker", "mqttbrokerpass")) {
      Serial.println("connected");
      MQTTClient.subscribe("led");
      MQTTClient.subscribe("vibration");
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
/*
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


*/





      
