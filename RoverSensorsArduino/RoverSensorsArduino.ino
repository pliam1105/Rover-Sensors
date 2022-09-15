#include <TinyGPS++.h>
#include <dht.h>
#include <Wire.h>
#include <SPI.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP280.h>
#include "Adafruit_CCS811.h"
#include "Adafruit_VEML6070.h"
#include "SoftwareSerial.h"
#define DEBUG true  // turn debug message on or off in serial
static const uint32_t GPSBaud = 9600;
String geiger_data;
SoftwareSerial ss(10, 11);
TinyGPSPlus gps;
dht DHT;
Adafruit_BMP280 bme;
Adafruit_CCS811 ccs;
Adafruit_VEML6070 uv = Adafruit_VEML6070();
int dht_pin = 2;
int light_pin = A0;
int gas_dout = 3;
int gas_aout = A1;
float tempC;
float humidity;
int light_val;
int gas_val;
float uv_val;
float bmp_val;
float co2_val;
float tvoc_val;
float last_lat = 40.64912;
float last_long = 22.946003;
float last_alt = 125.7;
float latitude;
float longitude;
float altitude;
int geiger_cpm_val;
String server = "rovergr.space";
String uri;

void setup() {

  Serial3.begin(115200); //serial3 for esp8266
  ss.begin(GPSBaud);//ss for GPS sensor
  Serial.begin(115200);
  Serial2.begin(9600);
  if (!bme.begin()) {
    Serial.println("Could not find a valid BMP280 sensor, check wiring!");
    while (1);
  }
  if (!ccs.begin()) {
    Serial.println("Failed to start CCS811 sensor! Please check your wiring.");
    while (1);
  }
  uv.begin(VEML6070_1_T);  // pass in the integration time constant
  sendData("AT+RST\r\n", 2000, DEBUG); // reset module
  sendData("AT+CWMODE=3\r\n", 1000, DEBUG); // configure as access point
  sendData("AT+CWJAP=\"RoverGRCom\",\"12345678\"\r\n", 3000, DEBUG);
  delay(20000);
  sendData("AT+CIFSR\r\n", 1000, DEBUG); // get ip address
  sendData("AT+CIPMUX=0\r\n", 1000, DEBUG); // configure for single connections

}

void loop() {
  if (ss.available() > 0) {
    int c = ss.read();
    if (gps.encode(c)) {
      if (gps.location.isValid()) {
        latitude = gps.location.lat();
        longitude = gps.location.lng();
        altitude = gps.altitude.meters();
        last_lat = latitude;
        last_long = longitude;
        last_alt = altitude;
        Serial.print(String(latitude, 6));
        Serial.print(F(","));
        Serial.print(String(longitude, 6));
        Serial.print(F(","));
        Serial.print(String(altitude, 6)+" m");
      } else {
        Serial.print(F("Sending default..."));
        latitude = last_lat;
        longitude = last_long;
        altitude = last_alt;
      }
    }
    Serial.println();
  }
  if (latitude > 0 && longitude > 0) {
    delay(500);
    while (1) {
      delay(500);
      int chk = DHT.read11(dht_pin);
      tempC = DHT.temperature;
      humidity = DHT.humidity;
      Serial.println(tempC);
      Serial.println(humidity);
      if(tempC!=-999&&humidity!=-999){
        break;
      }
    }
    if (Serial2.available()) {
    char received = Serial2.read();
    geiger_data += received;
    if (received == '\n') {
      geiger_cpm_val= geiger_data.toInt();
      Serial.print("Geiger CPM: ");
      Serial.println(geiger_cpm_val);
      geiger_data = "";
    }
  }
  delay(50);
    light_val = map(analogRead(light_pin), 0, 1023, 0, 100);
    gas_val = map(analogRead(gas_aout), 0, 1023, 0, 100);
    bmp_val = bme.readPressure();
    if (ccs.available()) {
      if (!ccs.readData()) {
        co2_val = ccs.geteCO2();
        tvoc_val = ccs.getTVOC();
      }
    }
    uv_val = uv.readUV();
    uri = "/arduino/write_sql_arduino.php?temperature=" + String(tempC) + "&humidity=" + String(humidity) + "&light=" + String(light_val) + "&gas=" + String(gas_val) + "&co2=" + String(co2_val) + "&tvoc=" + String(tvoc_val) + "&uv=" + String(uv_val) + "&barometric_pressure=" + String(bmp_val) + "&latitude=" + String(latitude, 6) + "&longitude=" + String(longitude, 6) + "&altitude=" + String(altitude, 6) + "&radiation=" + String(geiger_cpm_val);
    Serial3.println("AT+CIPSTART=\"TCP\",\"" + server + "\",80");//start a TCP connection.
    if ( Serial3.find("OK")) {
      Serial.println("TCP connection ready");
    }
    delay(1000);
    String getRequest = "GET " + uri + " HTTP/1.1\r\n" +
                        "Host: " + server + "\r\n\r\n";

    String sendCmd = "AT+CIPSEND=";//determine the number of caracters to be sent.
    Serial3.print(sendCmd);
    Serial3.println(getRequest.length() );
    delay(500);
    if (Serial3.find(">")) {
      Serial.println("Sending..");
    }
    Serial3.print(getRequest);
    Serial.print(getRequest);
    if ( Serial3.find("SEND OK")) {
      Serial.println("Packet sent");
    }
    while (Serial3.available()) {
      String tmpResp = Serial3.readString();
      Serial.println(tmpResp);
    }

    delay(20000);
    latitude = 0;
    longitude = 0;
  }
}





String sendData(String command, const int timeout, boolean debug)
{
  String response = "";

  Serial3.print(command); // send the read character to the esp8266

  long int time = millis();

  while ( (time + timeout) > millis())
  {
    while (Serial3.available())
    {

      // The esp has data so display its output to the serial window
      char c = Serial3.read(); // read the next character.
      response += c;
    }
  }

  if (debug)
  {
    Serial.print(response);
  }

  return response;
}
