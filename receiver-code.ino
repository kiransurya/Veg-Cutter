byte inByte;

void setup() {
Serial.begin(9600);
pinMode (11, OUTPUT);
}

void loop() {

if (Serial.available())
{
inByte = Serial.read();
analogWrite(11, int(inByte));

}


}
