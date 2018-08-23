char c;
int ct=0;
void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
}

void loop() {
 
  if(Serial.available())
  {
char c=Serial.read();
Serial.println(c);

 Serial.write("R");

//break;
  }
  else
  {
    if(ct<1)
    {
   Serial.write("S");
   ct=ct+1;
   //break;
    } 
  }

 // break;
  }
  

