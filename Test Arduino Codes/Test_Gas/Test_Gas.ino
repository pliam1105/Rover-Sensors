int gas_aout = A1;
int gas_val;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  gas_val = map(analogRead(gas_aout), 0, 1023, 0, 100);
  Serial.println(String(gas_val));
}
