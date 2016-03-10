/*
 Interface graphique du projet de fin d'étude "Réalisations en faveur 
 de l'accessibilité aux jeux vidéos" pour des personnes à handicap moteur. 
 L'interface va permettre de reconfigurer les commandes de la manette de jeux
 qui a été réalisé tout au long du projet.
 
 for Processing V3
 (c) 2015 Jérôme Bailet & Mehdi Zeggaï
 IMA5 Polytech Lille
 */

// Apache Commons Library
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

// G4P library
import g4p_controls.*;

//Serial library
import processing.serial.*;
Serial myPort;  // Create object from Serial class
String serial;   // declare a new string called 'serial' . A string is a sequence of characters (data type know as "char")
int end = 10;    // the number 10 is ASCII for linefeed (end of serial.println), later we will look for this to break up individual messages

GLabel labelResume; 

public void setup(){
  size(800, 600, JAVA2D);
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