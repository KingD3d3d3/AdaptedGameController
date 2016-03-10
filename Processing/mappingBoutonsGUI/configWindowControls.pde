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
  labelTouche.setText("Bouton ou combinaison à affecter : ");
  
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
  buttonConfigDefaut.setText("Défaut");
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