#include <SoftwareSerial.h>
#include <TinyGPS.h>
#include <PubSubClient.h>
#include <HTTPClient.h>
#include "ArduinoJson.h"
#include <WiFi.h>


////////////// INICIALIZACION VARIABLES GLOBALES //////////////

int device = 28, LocationSensor, PressureSensor, SoundSensor, DistanceSensor, LedActuator, VibrationActuator; //ID's
const int freq = 5000, ledChannel = 0, ledChannel2 = 1, resolution = 8, ledPin = 26, vibPin = 27;             //PWM
unsigned long tiempo=0, tiempo2=0, tiempoLoop=0;                                                              //VARIABLES PARA DELAYs NO BLOQUEANTES
bool estadoLED = true, estadoVIB = true;                                                                      // LED Y VIB ENCENDIDO/APAGADO
unsigned int lum = 10, len = 1000, inten = 10, leng = 1000;                                                   //LONGITUD E INTENSIDAD
int decibels=0, decibelsMed=0, pressure=0;                                                                  //NIVEL DE SONIDO Y PRESIÓN TEMPORALES

///////////////// INICIALIZACION CLIENTE WIFI /////////////////
char responseBuffer[300];
WiFiClient client;
WiFiClient client2;
char SSID[] = "MiFibra-C416";
char PASS[] = "CHAMGONZ";
String SERVER_IP = "192.168.1.137";
int SERVER_PORT = 8080;

///////////////// INICIALIZACION CLIENTE MQTT /////////////////
const char* mqtt_server = "192.168.1.137";
PubSubClient MQTTClient(client2);
long lastMsg = 0;
char msg[50];
int value = 0;

///////////////////// INICIALIZACION GPS /////////////////////
TinyGPS gps;
SoftwareSerial softSerial(1, 3);
float coor[2];


void setup() {
  Serial.begin(9600);
  Serial.println("Dispositivo Iniciado");
  
///////////////////// INICIO CONEXION WIFI /////////////////////
  WiFi.begin(SSID, PASS);  
  Serial.println("Connecting...");
  while (WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.print(".");
  }
  Serial.print("Connected, IP address: ");
  Serial.println(WiFi.localIP());

///////////////////// INICIO CONEXION MQTT /////////////////////
  MQTTClient.setServer(mqtt_server, 1885);
  MQTTClient.setCallback(callback);

////////////// OBTENER ID's SENSORES Y ACTUADORES //////////////
  getSensorsOfDevice();
  getActuatorsOfDevice();

///////// INICIALIZA LOS PINES DE ACTUADORES Y SENSORES ////////
  pinMode(32,INPUT);                          //SENSOR ANALOGICO SONIDO
  pinMode(33,INPUT);                          //SENSOR PRESION
  ledcSetup(ledChannel, freq, resolution);    //PWM LED
  ledcAttachPin(ledPin, ledChannel);          //PIN DEL LED
  ledcSetup(ledChannel2, freq, resolution);   //PWM VIB
  ledcAttachPin(vibPin, ledChannel2);         //PIN DEL VIB
  softSerial.begin(9600);                     //SERIAL GPS
  
  for(int i=0; i<2000; i++){
    decibelsMed += analogRead(32);
  }
  decibelsMed/=2000;
  decibelsMed+=200;
  Serial.println("DecibelsMed: " + String(decibelsMed, DEC));
}

void loop(){
  if (!MQTTClient.connected()) {
    MQTTReconnect();
  }
  MQTTClient.loop();  //CONSTANTEMENTE COMPRUEBA MENSAJES NUEVOS 
  
  escribeLED();   //CONSTANTEMENTE ACTUALIZA LED
  escribeVIB();   //CONSTANTEMENTE ACTUALIZA VIB

  if(millis() > tiempoLoop){ //CADA 5 SEGS PUBLICA SENSORES
    //postTest();
    postLocation();
    postPressure();
    postSound();
    postDistance();
    
    tiempoLoop = millis() + 15000;
  }
  
  if(millis() > tiempoLoop-4000){ //100 ms DESPUES DE HACER LOS POSTS
    int aux = leeSonido();
    if(decibels < aux) decibels = aux; //ALMACENA EL MAYOR DE LOS SONIDOS CADA VEZ QUE AUMENTA  
    if(pressure < analogRead(33)) pressure = analogRead(33); //ALMACENA EL MAYOR DE LAS PRESIONES CADA VEZ QUE AUMENTA
  }
   
}



///////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// INICIO FUNCIONES MQTT ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

void callback(char* topic, byte* message, unsigned int lon) {
  String messageTemp;
  const size_t capacity = JSON_OBJECT_SIZE(8) + JSON_ARRAY_SIZE(8) + 60;
  DynamicJsonDocument doc(capacity);
      
  for (int i = 0; i < lon; i++) {
    Serial.print((char)message[i]);
    messageTemp += (char)message[i];
  }

  Serial.println("HA ENTRADO EN EL CALLBACK");
  
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

///////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// FINAL FUNCIONES MQTT ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// INICIO FUNCIONES ACTUADORES /////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
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

void escribeLED(){
  //Serial.println("EscribeLED()" + String(lum, DEC));
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
  //Serial.println("EscribeVIB()" + String(inten, DEC));
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

///////////////////////////////////////////////////////////////////////////////////////
////////////////////////////// FINAL FUNCIONES ACTUADORES /////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////
////////////////////////////// INICIO FUNCIONES SENSORES //////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
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

void postTest(){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/", true);
    String value = "";
    int code = http.POST(value);
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
    String value = "{\"id\": 0,\"value\": " + String(pressure, DEC) + ",\"timestamp\": 1}";
    int code = http.POST(value);
    String payload = http.getString();
    if(code == 200){
      Serial.println("Pressure Posted Correctly");
    
    }else Serial.println("Error al conectar con el servidor:" + String(code,DEC));
    //Serial.println("Respuesta:");
    //Serial.println(payload);
  }else Serial.println("Error al conectar mediante WIFI");
  pressure = 0;
}

void postSound(){
  if (WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(client, SERVER_IP, SERVER_PORT, "/api/sensor/values/" + String(SoundSensor, DEC), true);
    String value = "{\"id\": 0,\"decibels\": " + String(decibels, DEC) + ",\"timestamp\": 1}";
    int code = http.POST(value);
    String payload = http.getString();
    if(code == 200){
      Serial.println("Sound Posted Correctly");
    
    }else Serial.println("Error al conectar con el servidor:" + String(code,DEC));
    //Serial.println("Respuesta:");
    //Serial.println(payload);
  }else Serial.println("Error al conectar mediante WIFI");
  decibels = 0;
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

int leeSonido(){
  int res=0;
  for(int i=0; i<2000; i++){
    res+= abs(decibelsMed - analogRead(32));
  }
  res/=2000;
  res-=50;
  if(res<0) res=0;
  else res*=res;
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

///////////////////////////////////////////////////////////////////////////////////////
////////////////////////////// FINAL FUNCIONES SENSORES ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
      
