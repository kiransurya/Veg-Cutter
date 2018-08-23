
//String s = "hey";  // Change to send another string 
//String s = "some text here";
//String t= s.split("");  // splitting the string in to array
void setup() {   
  // initialize both serial ports:
  Serial.begin(9600);   // port for serial monitor
  Serial2.begin(9600);  // Port for serial communication
  Serial2.flush();      // To empty the port ie all the is being sent from serial and nothing is left in serial stream
}

void loop() {
 Serial2.write("hey "); // string to be sent ,with a space to distinguish at receiver side, can be  "t[0] ",if splitted into array
 delay(1000);   
  if (Serial2.available()>0) {         // read from serial port 2, send to port 0:
   char inByte = Serial2.read();       // receiving data from serial in character form
   Serial2.flush();
    Serial.println(inByte);            // Printing on serial monitor 
  }

}
