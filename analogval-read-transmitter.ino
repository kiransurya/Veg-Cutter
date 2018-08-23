char incomingByte;
int i;
void setup() {
Serial.begin(9600);
}
void loop() {     // read analog pin 5
 // for(i=0;i<10;i++)
 //{
 
  char msg[]="abcde";
  for(int i=0;msg[i]!='\0';i++)
{
  Serial.write(msg[i]);
delay(50);
//if(msg[i]=='\0')
//{
 //if(Serial.available())
//{
//incomingByte = Serial.read();
//Serial.println(char(incomingByte));
//delay(1);
//} 
//}
//}

 //i=10;
}
 //for(i=10;i<20;i++)
 //{
//if(Serial.available())
//{
//incomingByte = Serial.read();
//Serial.println(char(incomingByte));
//delay(1);
//}
 //}
 





 
