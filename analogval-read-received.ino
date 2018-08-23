byte inByte;

void setup() {

Serial.begin(9600); // Serial port enable
//// declare pin 16 as output, this is the LED
//pinMode (16, OUTPUT);
}

void loop() {
char msg[]="12345";

// if there is bytes available coming from the serial port
for(i=0;i<10;i++)
{
if (Serial.available() ) 
{       inByte = Serial.read();
      Serial.println(char(inByte));  
      delay(1);         
    }
}
i=10;
 for(i=10;i<20;i++)
  
    {  for(int i=0;msg[i]!='\0';i++)
    Serial.write(msg[i]);  
    delay(1);

    }

}

