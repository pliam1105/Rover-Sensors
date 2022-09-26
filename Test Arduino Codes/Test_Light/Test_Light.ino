int light_val;
int light_pin = A0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  light_val = map(analogRead(light_pin), 0, 1023, 0, 100);
  Serial.println(String(light_val));
}
