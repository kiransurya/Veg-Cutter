#define READ  0
#define WRITE 1
int STATE = 0;
char c = 0;
String str = "";

void setup()
   {   STATE = 0;
   Serial.begin(9600);
     }

void loop() {
     c=2;
     str = "zzz" ;
 if (STATE == READ)
    { 
    Serial.println( str );
     while (c != NULL )
      { str += c ;  
      } }

   else 
   { Serial.println( "asd" ); 
     }
}
