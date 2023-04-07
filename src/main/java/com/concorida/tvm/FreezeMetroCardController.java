package com.concorida.tvm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.util.ResourceBundle;

public class FreezeMetroCardController {
    ResourceBundle bundle = ResourceBundle.getBundle("messages_fr", Locale.FRANCE);
    String str="";
    boolean is_french = false;
    AlertBox alertBox = new AlertBox();
    @FXML private Label labelFM0,labelFM1,labelFM2,labelFM3;
    @FXML private Button freezeButton,receive;

    @FXML
    private TextField metroCardId,phone;
    @FXML
    private TextField verificationCode;
    @FXML
    private Label confirm_msg;

    public void setMsg(boolean is_french){
        this.is_french = is_french;
        if(is_french){
            labelFM0.setText(bundle.getString("labelFM0"));
            labelFM1.setText(bundle.getString("labelFM1"));
            labelFM2.setText(bundle.getString("labelFM2"));
            labelFM3.setText(bundle.getString("labelFM3"));

            freezeButton.setText(bundle.getString("freezeButton"));
            receive.setText(bundle.getString("receive"));
        }
    }

    @FXML
    private void freezeCard(){
        if(metroCardId.getText().equals("") ||metroCardId.getText().equals(" ") || phone.getText().equals("") ||phone.getText().equals(" ") ||verificationCode.getText().equals("") ||verificationCode.getText().equals(" ") ){
            if(is_french){
                alertBox.showAlert("Quelque chose manque",is_french);
            }
            else{
                alertBox.showAlert("Something Missing",is_french);
            }
            return;
        }
        confirm_msg.setText("");
        if ("123456".equalsIgnoreCase(verificationCode.getText())){
            confirm_msg.setText(metroCardId.getText() + " has been frozen successfully!");
        }else {
            if(is_french){
                confirm_msg.setText(bundle.getString("codenotcorrect"));
            }else{
                confirm_msg.setText("The verification code is not correct!");
            }
        }
    }
}
