import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.apache.commons.lang3.*; 
import org.apache.commons.lang3.builder.*; 
import org.apache.commons.lang3.concurrent.*; 
import org.apache.commons.lang3.event.*; 
import org.apache.commons.lang3.exception.*; 
import org.apache.commons.lang3.math.*; 
import org.apache.commons.lang3.mutable.*; 
import org.apache.commons.lang3.reflect.*; 
import org.apache.commons.lang3.text.*; 
import org.apache.commons.lang3.text.translate.*; 
import org.apache.commons.lang3.time.*; 
import org.apache.commons.lang3.tuple.*; 
import g4p_controls.*; 
import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class mappingBoutonsGUI extends PApplet {

/*
 Interface graphique du projet de fin d'\u00e9tude "R\u00e9alisations en faveur 
 de l'accessibilit\u00e9 aux jeux vid\u00e9os" pour des personnes \u00e0 handicap moteur. 
 L'interface va permettre de reconfigurer les commandes de la manette de jeux
 qui a \u00e9t\u00e9 r\u00e9alis\u00e9 tout au long du projet.
 
 for Processing V3
 (c) 2015 J\u00e9r\u00f4me Bailet & Mehdi Zegga\u00ef
 IMA5 Polytech Lille
 */

// Apache Commons Library













// G4P library


//Serial library

Serial myPort;  // Create object from Serial class
String serial;   // declare a new string called 'serial' . A string is a sequence of characters (data type know as "char")
int end = 10;    // the number 10 is ASCII for linefeed (end of serial.println), later we will look for this to break up individual messages

GLabel labelResume; 

public void setup(){
  
  background(240);
  createGUI();
  customGUI();
  // Place your setup code here
  
  //Serial Communication
  String portName = Serial.list()[0]; //change the 0 to a 1 or 2 etc. to match your port
  println(portName);
  myPort = new Serial(this, portName, 9600);
  myPort.clear();  // function from serial library that throws out the first reading, in case we started reading in the middle of a string from Arduino
  serial = myPort.readStringUntil(end); // function that reads the string from serial port until a println and then assigns string to our string variable (called 'serial')
  serial = null; // initially, the string will be null (empty)
}

public void draw(){
  background(240);
}

// Use this method to add additional statements
// to customise the GUI controls
public void customGUI(){
  
  // Resume text of the GUI
  String[] lines = loadStrings("resume.txt");
  String text = join(lines, '\n');
  labelResume = new GLabel(this, 10, 10, 150, 400, text);
  labelResume.setTextAlign(GAlign.LEFT, GAlign.TOP);
  
}
// =============================================
// Create the configuration window and controls
// =============================================

GWindow windowControl;  
GButton buttonConfigOk;
GButton buttonConfigAnnuler;
GButton buttonConfigDefaut;
GTextField textfieldTouche;
GTextField textfieldButt12Delay;
GLabel labelJack, labelTouche, labelDelay;
String jackID;

/* Configuration Window */
public void createControlWindow(String _jackID) {
  jackID =  _jackID;
  println("jackID : "+jackID);
  int posX = mouseX , posY = mouseY;
  windowControl = GWindow.getWindow(this, "Configuration", posX, posY, 240, 200, JAVA2D);
  PApplet app = windowControl; // save some typing
  
  labelJack = new GLabel(app, 75, 5, 80, 10);
  labelJack.setText("Jack : " + jackID);
  labelJack.setTextBold();
  
  labelTouche = new GLabel(app, 20, 30, 200, 10);
  labelTouche.setTextAlign(GAlign.LEFT,null);
  labelTouche.setText("Bouton ou combinaison \u00e0 affecter : ");
  
  textfieldTouche = new GTextField(app, 20, 42, 200, 25, G4P.SCROLLBARS_NONE);
  textfieldTouche.setPromptText("ex: 12 pour bouton 1 puis 2");
  textfieldTouche.setOpaque(true);
  textfieldTouche.addEventHandler(this, "textfieldTouche_change");
  
  labelDelay = new GLabel(app, 20, 70, 200, 30);
  labelDelay.setTextAlign(GAlign.LEFT,null);
  labelDelay.setText("Si combinaison, intervalle de temps entre 2 boutons : (en ms)");
  
  textfieldButt12Delay = new GTextField(app, 20, 100, 50, 25, G4P.SCROLLBARS_NONE);
  textfieldButt12Delay.setText("200");
  textfieldButt12Delay.setOpaque(true);
  textfieldButt12Delay.addEventHandler(this, "textfieldButt12Delay_change");
  
  buttonConfigOk = new GButton(app, 30, 140, 70, 25);
  buttonConfigOk.setText("Ok");
  buttonConfigOk.addEventHandler(this, "buttonConfigOk_click");
  
  buttonConfigDefaut = new GButton(app, 140, 140, 70, 25);
  buttonConfigDefaut.setText("D\u00e9faut");
  buttonConfigDefaut.addEventHandler(this, "buttonConfigDefaut_click");
  
  buttonConfigAnnuler = new GButton(app, 85, 170, 70, 25);
  buttonConfigAnnuler.setText("Annuler");
  buttonConfigAnnuler.addEventHandler(this, "buttonConfigAnnuler_click");
  buttonConfigAnnuler.setLocalColorScheme(GCScheme.RED_SCHEME);
  
  windowControl.addDrawHandler(this, "drawController");
  windowControl.setActionOnClose(G4P.CLOSE_WINDOW);
  windowControl.addOnCloseHandler(this, "closeController");
}

synchronized public void drawController(PApplet appc, GWinData data) {
  appc.background(240);
}

public void closeController(PApplet applet, GWinData data) {
  println("closeController - window closed at " + millis());
  String msg = "";
  msg = "Y";
  myPort.write(msg+"$");
  println("send serial : " + msg + "$");
}

public void buttonConfigOk_click(GButton source, GEvent event) {
  println("buttonOk - GButton >> GEvent." + event + " @ " + millis());
  String combiMsg = textfieldTouche.getText();
  println("text : "+ combiMsg);
  int len = combiMsg.length();
  
  if(StringUtils.isNumeric(combiMsg) && len<=2 && 
  combiMsg.charAt(0)<'9' && combiMsg.charAt(len - 1 )<'9'){
      
      updateLabelButtons(combiMsg);
      
      if(len == 2){
        String buttDelay = textfieldButt12Delay.getText();
        myPort.write(jackID + combiMsg + buttDelay + "$");
        println("numeric text : "+ combiMsg);
        println("send serial : " + jackID + combiMsg + buttDelay + "$");
        windowControl.close();
      } else {
        myPort.write(jackID + combiMsg + "$");
        println("numeric text : "+ combiMsg);
        println("send serial : " + jackID + combiMsg + "$");
        windowControl.close();
      }
  }
  
}

public void buttonConfigAnnuler_click(GButton source, GEvent event) {
  println("buttonAnnuler - GButton >> GEvent." + event + " @ " + millis());
  //closeController(windowControl,null);
  windowControl.close();
}

public void buttonConfigDefaut_click(GButton source, GEvent event) {
  println("buttonDefaut - GButton >> GEvent." + event + " @ " + millis());
  String combiMsg = jackID ;
  updateLabelButtons(combiMsg);
  myPort.write(jackID + combiMsg + "$");
  println("send serial : " + jackID + combiMsg + "$");
  windowControl.close();
}

public void textfieldTouche_change(GTextField source, GEvent event) {
  println("textfieldTouche - GTextField >> GEvent." + event + " @ " + millis());
  
}

public void textfieldButt12Delay_change(GTextField source, GEvent event) {
  println("textfieldButt12Delay - GTextField >> GEvent." + event + " @ " + millis());
  
}

public void updateLabelButtons(String buttonsMsg){
  if(buttonsMsg.length() == 2){
    int i = jackID.charAt(0) - '0';
    println("jackID = " + i);
    char b1 = buttonsMsg.charAt(0);
    char b2 = buttonsMsg.charAt(1);
    switch (i){
      case 1:
        labelB1_1.setText("B1 : " + b1);
        labelB2_1.setText("B2 : " + b2);
        labelB1_1.setTextBold();
        labelB2_1.setTextBold();
        break;
      case 2:
        labelB1_2.setText("B1 : " + b1);
        labelB2_2.setText("B2 : " + b2);
        labelB1_2.setTextBold();
        labelB2_2.setTextBold();
        break;
      case 3:
        labelB1_3.setText("B1 : " + b1);
        labelB2_3.setText("B2 : " + b2);
        labelB1_3.setTextBold();
        labelB2_3.setTextBold();
        break;
      case 4:
        labelB1_4.setText("B1 : " + b1);
        labelB2_4.setText("B2 : " + b2);
        labelB1_4.setTextBold();
        labelB2_4.setTextBold();
        break;
      case 5:
        labelB1_5.setText("B1 : " + b1);
        labelB2_5.setText("B2 : " + b2);
        labelB1_5.setTextBold();
        labelB2_5.setTextBold();
        break;
      case 6:
        labelB1_6.setText("B1 : " + b1);
        labelB2_6.setText("B2 : " + b2);
        labelB1_6.setTextBold();
        labelB2_6.setTextBold();
        break;
      case 7:
        labelB1_7.setText("B1 : " + b1);
        labelB2_7.setText("B2 : " + b2);
        labelB1_7.setTextBold();
        labelB2_7.setTextBold();
        break;
      case 8:
        labelB1_8.setText("B1 : " + b1);
        labelB2_8.setText("B2 : " + b2);
        labelB1_8.setTextBold();
        labelB2_8.setTextBold();
        break;
      default:
        println("error 1");
        break;
    }
  } else if (buttonsMsg.length() == 1){
    int i = jackID.charAt(0) - '0';
    println("jackID = " + i);
    char b1 = buttonsMsg.charAt(0);
    switch (i){
      case 1:
        labelB1_1.setText("B1 : " + b1);
        labelB2_1.setText("B2 :");
        labelB1_1.setTextBold();
        labelB2_1.setTextBold();
        break;
      case 2:
        labelB1_2.setText("B1 : " + b1);
        labelB2_2.setText("B2 :");
        labelB1_2.setTextBold();
        labelB2_2.setTextBold();
        break;
      case 3:
        labelB1_3.setText("B1 : " + b1);
        labelB2_3.setText("B2 :");
        labelB1_3.setTextBold();
        labelB2_3.setTextBold();
        break;
      case 4:
        labelB1_4.setText("B1 : " + b1);
        labelB2_4.setText("B2 :");
        labelB1_4.setTextBold();
        labelB2_4.setTextBold();
        break;
      case 5:
        labelB1_5.setText("B1 : " + b1);
        labelB2_5.setText("B2 :");
        labelB1_5.setTextBold();
        labelB2_5.setTextBold();
        break;
      case 6:
        labelB1_6.setText("B1 : " + b1);
        labelB2_6.setText("B2 :");
        labelB1_6.setTextBold();
        labelB2_6.setTextBold();
        break;
      case 7:
        labelB1_7.setText("B1 : " + b1);
        labelB2_7.setText("B2 :");
        labelB1_7.setTextBold();
        labelB2_7.setTextBold();
        break;
      case 8:
        labelB1_8.setText("B1 : " + b1);
        labelB2_8.setText("B2 :");
        labelB1_8.setTextBold();
        labelB2_8.setTextBold();
        break;
      default:
        println("error 2");
        break;
    }
  } else println("error 3");
  return;
}
/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void button1_click(GButton source, GEvent event) { //_CODE_:button1:493958:
  println("button1 - GButton >> GEvent." + event + " @ " + millis());
  
  String jackID = "1";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button1:493958:

public void button2_click(GButton source, GEvent event) { //_CODE_:button2:867308:
  println("button2 - GButton >> GEvent." + event + " @ " + millis());
  String jackID = "2";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button2:867308:

public void button3_click(GButton source, GEvent event) { //_CODE_:button3:899568:
  println("button3 - GButton >> GEvent." + event + " @ " + millis());
  String jackID = "3";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button3:899568:

public void button4_click(GButton source, GEvent event) { //_CODE_:button4:535291:
  println("button4 - GButton >> GEvent." + event + " @ " + millis());
  String jackID = "4";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button4:535291:

public void button5_click(GButton source, GEvent event) { //_CODE_:button5:945448:
  println("button5 - GButton >> GEvent." + event + " @ " + millis());
  String jackID = "5";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button5:945448:

public void button6_click(GButton source, GEvent event) { //_CODE_:button6:632259:
  println("button6 - GButton >> GEvent." + event + " @ " + millis());
  String jackID = "6";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button6:632259:

public void button7_click(GButton source, GEvent event) { //_CODE_:button7:808710:
  println("button7 - GButton >> GEvent." + event + " @ " + millis());
  String jackID = "7";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button7:808710:

public void button8_click(GButton source, GEvent event) { //_CODE_:button8:397043:
  println("button8 - GButton >> GEvent." + event + " @ " + millis());
  String jackID = "8";
  String msg = "";
  if (windowControl == null){
    println("First create windowControl !");
    createControlWindow(jackID);
    println(windowControl.isVisible());
    msg = "X";
    myPort.write(msg+"$");
    println("send serial : " + msg + "$");
  } else if (!windowControl.isVisible()){
     createControlWindow(jackID);
     println(windowControl.isVisible());
     msg = "X";
     myPort.write(msg+"$");
     println("send serial : " + msg + "$");
  }
} //_CODE_:button8:397043:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setCursor(ARROW);
  surface.setTitle("manetteAdapteeGUI");
  label1 = new GLabel(this, 275, 460, 45, 45);
  label1.setText("Jack 1");
  label1.setLocalColorScheme(GCScheme.RED_SCHEME);
  label1.setOpaque(true);
  label2 = new GLabel(this, 475, 460, 45, 45);
  label2.setText("Jack 2");
  label2.setLocalColorScheme(GCScheme.RED_SCHEME);
  label2.setOpaque(true);
  label3 = new GLabel(this, 275, 310, 45, 45);
  label3.setText("Jack 3");
  label3.setLocalColorScheme(GCScheme.RED_SCHEME);
  label3.setOpaque(true);
  label4 = new GLabel(this, 475, 310, 45, 45);
  label4.setText("Jack 4");
  label4.setLocalColorScheme(GCScheme.RED_SCHEME);
  label4.setOpaque(true);
  label5 = new GLabel(this, 275, 160, 45, 45);
  label5.setText("Jack 5");
  label5.setLocalColorScheme(GCScheme.RED_SCHEME);
  label5.setOpaque(true);
  label6 = new GLabel(this, 475, 160, 45, 45);
  label6.setText("Jack 6");
  label6.setLocalColorScheme(GCScheme.RED_SCHEME);
  label6.setOpaque(true);
  button1 = new GButton(this, 275, 550, 50, 20);
  button1.setText("Config");
  button1.addEventHandler(this, "button1_click");
  button2 = new GButton(this, 475, 550, 50, 20);
  button2.setText("Config");
  button2.addEventHandler(this, "button2_click");
  button3 = new GButton(this, 275, 400, 50, 20);
  button3.setText("Config");
  button3.addEventHandler(this, "button3_click");
  button4 = new GButton(this, 475, 400, 50, 20);
  button4.setText("Config");
  button4.addEventHandler(this, "button4_click");
  button5 = new GButton(this, 275, 250, 50, 20);
  button5.setText("Config");
  button5.addEventHandler(this, "button5_click");
  button6 = new GButton(this, 475, 250, 50, 20);
  button6.setText("Config");
  button6.addEventHandler(this, "button6_click");
  label7 = new GLabel(this, 275, 10, 45, 45);
  label7.setText("Jack 7");
  label7.setLocalColorScheme(GCScheme.RED_SCHEME);
  label7.setOpaque(true);
  label8 = new GLabel(this, 475, 10, 45, 45);
  label8.setText("Jack 8");
  label8.setLocalColorScheme(GCScheme.RED_SCHEME);
  label8.setOpaque(true);
  labelB1_1 = new GLabel(this, 240, 510, 60, 40);
  labelB1_1.setText("B1 : 1");
  labelB1_1.setTextBold();
  labelB1_1.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_1.setOpaque(true);
  labelB2_1 = new GLabel(this, 300, 510, 60, 40);
  labelB2_1.setText("B2 :");
  labelB2_1.setTextBold();
  labelB2_1.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_1.setOpaque(true);
  labelB1_2 = new GLabel(this, 440, 510, 60, 40);
  labelB1_2.setText("B1 : 2");
  labelB1_2.setTextBold();
  labelB1_2.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_2.setOpaque(true);
  labelB2_2 = new GLabel(this, 500, 510, 60, 40);
  labelB2_2.setText("B2 :");
  labelB2_2.setTextBold();
  labelB2_2.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_2.setOpaque(true);
  labelB1_3 = new GLabel(this, 240, 360, 60, 40);
  labelB1_3.setText("B1 : 3");
  labelB1_3.setTextBold();
  labelB1_3.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_3.setOpaque(true);
  labelB2_3 = new GLabel(this, 300, 360, 60, 40);
  labelB2_3.setText("B2 :");
  labelB2_3.setTextBold();
  labelB2_3.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_3.setOpaque(true);
  labelB1_4 = new GLabel(this, 440, 360, 60, 40);
  labelB1_4.setText("B1 : 4");
  labelB1_4.setTextBold();
  labelB1_4.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_4.setOpaque(true);
  labelB2_4 = new GLabel(this, 500, 360, 60, 40);
  labelB2_4.setText("B2 :");
  labelB2_4.setTextBold();
  labelB2_4.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_4.setOpaque(true);
  labelB1_5 = new GLabel(this, 240, 210, 60, 40);
  labelB1_5.setText("B1 : 5");
  labelB1_5.setTextBold();
  labelB1_5.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_5.setOpaque(true);
  labelB2_5 = new GLabel(this, 300, 210, 60, 40);
  labelB2_5.setText("B2 :");
  labelB2_5.setTextBold();
  labelB2_5.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_5.setOpaque(true);
  labelB1_6 = new GLabel(this, 440, 210, 60, 40);
  labelB1_6.setText("B1 : 6");
  labelB1_6.setTextBold();
  labelB1_6.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_6.setOpaque(true);
  labelB2_6 = new GLabel(this, 500, 210, 60, 40);
  labelB2_6.setText("B2 :");
  labelB2_6.setTextBold();
  labelB2_6.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_6.setOpaque(true);
  button7 = new GButton(this, 275, 100, 50, 20);
  button7.setText("Config");
  button7.addEventHandler(this, "button7_click");
  button8 = new GButton(this, 476, 100, 50, 20);
  button8.setText("Config");
  button8.addEventHandler(this, "button8_click");
  labelB1_7 = new GLabel(this, 240, 60, 60, 40);
  labelB1_7.setText("B1 : 7");
  labelB1_7.setTextBold();
  labelB1_7.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_7.setOpaque(true);
  labelB2_7 = new GLabel(this, 300, 60, 60, 40);
  labelB2_7.setText("B2 :");
  labelB2_7.setTextBold();
  labelB2_7.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_7.setOpaque(true);
  labelB1_8 = new GLabel(this, 440, 60, 60, 40);
  labelB1_8.setText("B1 : 8");
  labelB1_8.setTextBold();
  labelB1_8.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  labelB1_8.setOpaque(true);
  labelB2_8 = new GLabel(this, 500, 60, 60, 40);
  labelB2_8.setText("B2 :");
  labelB2_8.setTextBold();
  labelB2_8.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  labelB2_8.setOpaque(true);
  label9 = new GLabel(this, 20, 500, 183, 70);
  label9.setIcon("logoLille1.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
  label9.setOpaque(false);
  label10 = new GLabel(this, 580, 500, 210, 70);
  label10.setIcon("logoPolytechLille.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
  label10.setOpaque(false);
}

// Variable declarations 
// autogenerated do not edit
GLabel label1; 
GLabel label2; 
GLabel label3; 
GLabel label4; 
GLabel label5; 
GLabel label6; 
GButton button1; 
GButton button2; 
GButton button3; 
GButton button4; 
GButton button5; 
GButton button6; 
GLabel label7; 
GLabel label8; 
GLabel labelB1_1; 
GLabel labelB2_1; 
GLabel labelB1_2; 
GLabel labelB2_2; 
GLabel labelB1_3; 
GLabel labelB2_3; 
GLabel labelB1_4; 
GLabel labelB2_4; 
GLabel labelB1_5; 
GLabel labelB2_5; 
GLabel labelB1_6; 
GLabel labelB2_6; 
GButton button7; 
GButton button8; 
GLabel labelB1_7; 
GLabel labelB2_7; 
GLabel labelB1_8; 
GLabel labelB2_8; 
GLabel label9; 
GLabel label10; 
  public void settings() {  size(800, 600, JAVA2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "mappingBoutonsGUI" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
