/*
 Partie Arduino du projet de fin d'étude "Réalisations en faveur 
 de l'accessibilité aux jeux vidéos" pour des personnes à handicap moteur. 
 
 (c) 2015 Jérôme Bailet & Mehdi Zeggaï
 IMA5 Polytech Lille
*/

// Set to true to test "Auto Send" mode or false to test "Manual Send" mode.
//const bool testAutoSendMode = true;
bool testAutoSendMode = false; // Manual

// Number of buttons to handle
const int buttonsCount = 8;

// Arduino PINs to use
const int pins[buttonsCount] = {
    2, 
    3,
    4,
    6,
    5,
    9,
    7,
    8
};

// Array of Buttons //first button is by default // the second is at 13 by default (means no buttons associated)
int buttons[buttonsCount][buttonsCount] = {
    {0,13},
    {1,13},
    {2,13},
    {3,13},
    {4,13},
    {5,13},
    {6,13},
    {7,13}
};

bool butt1Pressed[buttonsCount] = {false, false, false, false, false, false, false, false};
bool butt2Pressed[buttonsCount] = {false, false, false, false, false, false, false, false};

int butt12Delay[buttonsCount]= {200, 200, 200, 200, 200, 200, 200, 200};

// Debounce delay
const long debounceDelay = 50;

bool status[buttonsCount] = {HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH};
long lastDebouncesButt1[buttonsCount] = {0};
long lastDebouncesButt2[buttonsCount] = {0};

// Number of autofire to handle
const int buttonsCountAuto = 2;

// Simulate press delay
const long pressDelay = 100;

// Autofire buttons
const int autoButtons[buttonsCountAuto] = {
    6,
    7
};

//Potentiometers
const int potarPins[buttonsCountAuto]{
  A0,
  A1
};

int potarValue[buttonsCountAuto] = {}; //store the value coming from the potentiometer
unsigned long previousMillis[buttonsCountAuto] = {0}; // Store last time

// RaspberryPi communication
int incomingInt = 0;
int x1 = 0, y1 = 0;

// Serial communication
String msg;
bool configEnable = false; // security for the buttons configuration

void setup() {
      Serial.begin(9600);
      Serial1.begin(9600);
      
      if (testAutoSendMode){
        Joystick.begin();
      }
      else {
        Joystick.begin(false);
      }
  
      // set up input buttons
      for (int i = 0; i < buttonsCount; ++i) {
         pinMode(pins[i], INPUT_PULLUP);
      }
      
      pinMode(A2, INPUT_PULLUP); // up
      pinMode(A3, INPUT_PULLUP); // down
      pinMode(A4, INPUT_PULLUP); //right
      pinMode(A5, INPUT_PULLUP); //left
      
}


void loop() {

     handleMessageProcessing();
     
     int x0 = defineJoystickX(A4,A5); // (right,left)
     int y0 = defineJoystickY(A2,A3); // (up,down)

     // send data only when you receive data:
     if (Serial1.available() == 2) {
          // read incoming 2 bytes
          incomingInt = Serial1.read()*256 + Serial1.read();
          x1 = highByte(incomingInt);
          y1 = lowByte(incomingInt);
          x1 = x1 - 127;
          y1 = 127 - y1;
          if(abs(x1)<127 && abs(y1) < 127){
            x1 = 0;
            y1 = 0;
          }
      } else if (Serial1.available() > 2){
          Serial.println("Problem too much data ...");
          flushSerial1();
          Serial.println("No More Problem !");
      }

     if((x0 != 0) || (y0 != 0)){
          Joystick.setXAxis(x0);
          Joystick.setYAxis(y0);
     } else if((x0 == 0) && (y0 == 0)){
          Joystick.setXAxis(x1);
          Joystick.setYAxis(y1);
     }
    
     //Buttons loop
     for (int i = 0; i < buttonsCount; ++i) {
         long _time = millis();
         //pinStatus[i] = digitalRead(pins[i]);
	       handleButton1(i, buttons[i][0], digitalRead(pins[i]), _time);
         if(buttons[i][1] != 13){
            handleButton2(i, buttons[i][1], lastDebouncesButt1[buttons[i][0]], _time);
         }
     }

    // Autofire buttons loop
     for (int i = 0; i < buttonsCountAuto; ++i) {
        potarValue[i] = analogRead(potarPins[i]);
        if(potarValue[i] <= 1000){
          //Serial.print("potarValue : ");
          //Serial.println(potarValue[i]);
          unsigned long period = definePeriod(potarValue[i]);
          handleAutoButton(autoButtons[i], i, millis(), period);
        }
     }
     
     /* Check function 
     int potarValue = analogRead(A2);
     //unsigned long period = definePeriod(potarValue);
     Serial.print("potarValue : ");
     Serial.println(potarValue);
     */
  
     if (!testAutoSendMode)
     {
      Joystick.sendState();
     }
}

/**
 * \brief Send a joystick button press/release if needed
 *
 * \param i Pin number
 * \param buttonNumber Button ID to handle
 * \param pinStatus PIN status (LOW/HIGH)
 * \param now Time in millis
 */
void handleButton1(int i, int buttonNumber1, const int pinStatus, const long now) {
  if (pinStatus != status[i] && now - debounceDelay > lastDebouncesButt1[buttonNumber1]) {
    status[i] = pinStatus;
    if (pinStatus == LOW) {
      Joystick.pressButton(buttonNumber1);
      butt1Pressed[buttonNumber1] =  true; // for handleButton2 function to know that Button1 was pressed
    } else {
      Joystick.releaseButton(buttonNumber1);
    }
    lastDebouncesButt1[buttonNumber1] = now;
  }
}

void handleButton2(int i, int buttonNumber2, const long last, const long now) {
  if (butt1Pressed[buttons[i][0]] && now - last > butt12Delay[i] ) { //
      lastDebouncesButt2[buttonNumber2] = now;
      Joystick.pressButton(buttonNumber2);
      butt1Pressed[buttons[i][0]] = false;
      butt2Pressed[buttonNumber2] = true;
   } else if(butt2Pressed[buttonNumber2] && now - lastDebouncesButt2[buttonNumber2] >= pressDelay){ //simulate press delay of a button
      Joystick.releaseButton(buttonNumber2);
      butt2Pressed[buttonNumber2] = false;
   }
}
 
/**
 * \brief Autofire a button with a specific period
 *
 * \param buttonNumber Button ID to handle
 * \param previous Indicator of the previous time in the array
 * \param now Current time in millis
 * \param period Autofire period in millis
 */
void handleAutoButton(const int buttonNumber, const int previous, const long now, const long period) {
   if(now - previousMillis[previous] >= period) {
     previousMillis[previous] = now;
     Joystick.pressButton(buttonNumber);
   } else if(now - previousMillis[previous] >= debounceDelay){ //simulate press delay of a button
     Joystick.releaseButton(buttonNumber);
   }
}

/**
 * \brief Define period in millisecond from an analog value in input
 *
 * \param val Analog value
 */ 
unsigned long definePeriod(int val){
  return 2 * val;
}

/**
 * \brief Return Joystick X axis value (-127,0,127)
 */ 
int defineJoystickX(int pinRight, int pinLeft){
      if(!digitalRead(pinRight)){ // Right pin A4
        //Serial.println("right x = 127");
        return 127;
      }
      else if (!digitalRead(pinLeft)){ // Left pin A5
        //Serial.println("left x = -127");
        return -127;
      } else return 0;
}
  
/**
 * \brief Return Joystick Y axis value (-127,0,127)
 */ 
int defineJoystickY(int pinUp, int pinDown){
  if(!digitalRead(pinUp)){ // Up pin A2
    return -127;
  }
  else if (!digitalRead(pinDown)){ // Down pin A3
    return 127;
  } else return 0;
}

/**
 * \brief Flush the serial 1
 */ 
void flushSerial1(){
  while(Serial1.available()) {
      Serial1.read();
  }
}

/**
 * \brief Remap buttons array when receive message from Processing
 *        Stop joystick communication during the process
 */
void handleMessageProcessing() {
  
  if(Serial.available()){
    msg = Serial.readStringUntil('$'); // read serial and store in msg
  } else{
    msg = "";
  }
  
 // First we disable Joystick.sendState()
 if (msg.charAt(0) == 'X'){ 
   testAutoSendMode = true;
   configEnable = true; //safe configuration
   return;
 }

 // At the end we enable back Joystick.sendState()
 if (msg.charAt(0) == 'Y'){
   testAutoSendMode = false;
   configEnable = false;
   return;
 }

 int len = msg.length();
 // Remapping
 if (configEnable && len > 3) { // 2 buttons in 1 pin
   int i = msg.charAt(0) - '1'; //in the GUI buttons configuration begins at 1
   int b1 = msg.charAt(1) - '1';
   int b2 = msg.charAt(2) - '1';
   String buttDelayMsg = msg.substring(3);
   
   int buttDelay = buttDelayMsg.toInt();
   butt12Delay[i] = buttDelay;
   
   buttons[i][0] = b1;
   buttons[i][1] = b2;
   return;
 } else if (configEnable && len == 2){ // 1 button
   int i = msg.charAt(0) - '1';
   int b1 = msg.charAt(1) - '1';
   buttons[i][0] = b1;
   buttons[i][1] = 13;
   return;
 } else if (len == 3){
   return;
 }
 
}
