#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include <Husarnet.h>
#include <HTTPClient.h>


// WiFi credentials

const char* ssid     = "ssid";
const char* password = "password";

// Husarnet credentials
const char* hostName = "esp32";
const char* husarnetJoinCode = "code";
const char* dashboardURL = "default";
int relayPin = 23;
int sensorPin = 33;
int threshold = 1000;
String serverPath ="http://maker.ifttt.com/trigger/discord/json/with/key/key";
int startTime;

// Setup http server
AsyncWebServer server(80);


void openRelay() {
  digitalWrite(relayPin, HIGH);
  delay(3000);
  digitalWrite(relayPin, LOW);

   

}
void openWarning(){
    WiFiClient client;
    HTTPClient http;
    
    http.begin(client, serverPath);

    http.addHeader("Content-Type", "application/json");
    int httpResponseCode = http.POST("{\"content\":\"lala\"}");
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    String payload = http.getString();
    Serial.println(payload);
    http.end();
}

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  pinMode(relayPin, OUTPUT);
  pinMode(sensorPin, INPUT);
  startTime = millis();

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("waiting for Wi-Fi...");
  }

  Husarnet.join(husarnetJoinCode, hostName);
  Husarnet.start();


  // Configure http server

server.on("/", HTTP_GET, [](AsyncWebServerRequest *request) {
    request->send(200, "text/plain", "opening locker");
    openRelay();
  });
  server.begin();
}

void loop() {

  if(analogRead(sensorPin)> threshold && millis() - startTime > 10000){
    startTime = millis();
    openWarning();
  } 

     
}
