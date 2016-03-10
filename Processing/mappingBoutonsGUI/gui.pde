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