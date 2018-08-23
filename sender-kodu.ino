int analogValue5, val5;
String input;
void setup() {
// Serial port enable
Serial.begin(9600);
}

void loop() {

analogValue5 = analogRead(5);// read analog pin 5
val5 = map(analogValue5, 0, 1023, 0, 255);
/*char msg[]="hii"; 
for(int i=0;msg[i]!='\0';i++)
{
Serial.write(msg[i]);
delay(50);
}*/
Serial.write(val5);
//Serial.println(val5);
//if(Serial.available())
//{
//  //if(Serial.read()=="DONE")
//  //{
//    input=Serial.readString();
//  //}
//  }
}
