#include <dht.h>
#include <stdlib.h>

dht DHT;
int dht_pin=2;
int light_pin=A0;
float tempC;
float humidity;
int light_val;
int error;
/*-----------------ESP8266 Serial WiFi Module---------------*/
#define SSID "WiFi1"      // "SSID-WiFiname" 
#define PASS "p@$$w0rd"       // "password"
#define IP "192.168.1.5"// MySql ip
String msg = "GET /write_sql_arduino.php?"; 
/*-----------------------------------------------------------*/
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.println("AT");
  delay(5000);
  if(Serial.find("OK")){
    connectWiFi();
  }
}

void loop() {
  // put your main code here, to run repeatedly:
  start: //label 
  error=0;

  updateTemp();
  //Resend if transmission is not completed 
  if (error==1){
    goto start; //go to label "start"
  }
  
  delay(3000);
}
void updateTemp(){
  int chk = DHT.read11(dht_pin);
  tempC=DHT.temperature;
  humidity=DHT.humidity;
  light_val=map(analogRead(light_pin),0,1023,0,100);
  String cmd = "AT+CIPSTART=\"TCP\",\"";
  cmd += IP;
  cmd += "\",80";
  Serial.println(cmd);
  delay(2000);
  if(Serial.find("Error")){
    return;
  }
  cmd = msg ;
  cmd += "temperature=";     //field 1 for temperature
  cmd += String(tempC);
  cmd += "&humidity=";  //field 2 for humidity
  cmd += String(humidity);
  cmd += "&light=";  //field 3 for light
  cmd += String(light_val);
  cmd += "\r\n";
  Serial.print("AT+CIPSEND=");
  Serial.println(cmd.length());
  delay(1000);
  if(Serial.find(">")){
    Serial.print(cmd);
  }
  else{
    Serial.println("AT+CIPCLOSE");
    //Resend...
    error=1;
  }
}

 
boolean connectWiFi(){
  Serial.println("AT+CWMODE=1");
  delay(2000);
  String cmd="AT+CWJAP=\"";
  cmd+=SSID;
  cmd+="\",\"";
  cmd+=PASS;
  cmd+="\"";
  Serial.println(cmd);
  delay(5000);
  if(Serial.find("OK")){
    return true;
  }else{
    return false;
  }
}
