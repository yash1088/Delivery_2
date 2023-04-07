package com.concorida.tvm;

import com.concorida.tvm.backend.QRCodeGenerator;
import com.google.zxing.WriterException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SubwayCardRechargeController {
    @FXML private Label label0,label1,label2,label3,label4;
    @FXML private RadioButton rd1,rd2,rd3,rd4,rd5,rd6,rd7;
    @FXML private Button rechargeButton;
    ResourceBundle bundle = ResourceBundle.getBundle("messages_fr",Locale.FRANCE);
    AlertBox alertBox = new AlertBox();
    String msg = "";
    boolean is_french = false;
    public SubwayCardRechargeController(){

    }

    public SubwayCardRechargeController(String msg){
        this.msg = msg;
    }

    public void setMsg(boolean is_french){
        this.is_french = is_french;
        if(is_french){
            label0.setText(bundle.getString("label_a"));
            label1.setText(bundle.getString("label_b"));
            label2.setText(bundle.getString("label_c"));
            label3.setText(bundle.getString("label_d"));
            label4.setText(bundle.getString("label_e"));

            rd1.setText(bundle.getString("rd1"));
            rd2.setText(bundle.getString("rd2"));
            rd3.setText(bundle.getString("rd3"));
            rd4.setText(bundle.getString("rd4"));
            rd5.setText(bundle.getString("rd5"));
            rd6.setText(bundle.getString("rd6"));
            rd7.setText(bundle.getString("rd7"));



            rechargeButton.setText(bundle.getString("rechargebutton"));
        }
    }

    @FXML
    private ToggleGroup rechargeAmountToggleGroup;

    @FXML
    private ToggleGroup paymentMethodToggleGroup;
    @FXML
    private ToggleGroup invoiceToggleGroup;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private HBox emailAddressHBox;
    @FXML
    private Label Invoice_Msg;

    @FXML
    private Label QRCode;
    @FXML
    private Label Message;

    @FXML
    public void initialize() {
        updateEmailAddressVisibility();

        invoiceToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                updateEmailAddressVisibility();
            }
        });
        rechargeButton.setOnAction(event -> {

            try {
                if(emailAddressTextField.getText().equals("") || emailAddressTextField.getText().equals(" ")){
                    if (is_french){
                        alertBox.showAlert("Veuillez fournir votre e-mail!!",is_french);
                    }
                    else{
                        alertBox.showAlert("Please provide your email!!",is_french);
                    }

                    return;
                }
                else{
                    handleRecharge();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void handleRecharge() throws IOException, WriterException, URISyntaxException {

        Invoice_Msg.setText("");
        RadioButton selectedRechargeAmount = (RadioButton) rechargeAmountToggleGroup.getSelectedToggle();
        String rechargeAmount = (String) selectedRechargeAmount.getUserData();

        RadioButton selectedPaymentMethod = (RadioButton) paymentMethodToggleGroup.getSelectedToggle();
        String paymentMethod = (String) selectedPaymentMethod.getUserData();

        RadioButton selectedInvoiceMethod = (RadioButton) invoiceToggleGroup.getSelectedToggle();
        String invoiceMethod = (String) selectedInvoiceMethod.getUserData();

        rechargeSubwayCard(rechargeAmount, paymentMethod);
        printInvoice(invoiceMethod);
    }



    private void printInvoice(String Invoice_Message){
        if ("PRINT_INVOICE".equalsIgnoreCase(Invoice_Message)){
            if(is_french){
                System.out.println("iii");
                Invoice_Msg.setText(bundle.getString("invoice_msg"));
            }
            else{
                Invoice_Msg.setText("The invoice printed");
            }

        }if ("EMAIL_INVOICE".equalsIgnoreCase(Invoice_Message)){
            if(is_french){
                Invoice_Msg.setText(bundle.getString("invoice_msg_email"));
            }
            else{
                Invoice_Msg.setText("The invoice printed");
            }
        }
    }

    private void rechargeSubwayCard(String rechargeAmount, String paymentMethod) throws IOException, WriterException, URISyntaxException {
        if ("CREDIT_CARD".equalsIgnoreCase(paymentMethod)){
            if(is_french){
                Message.setText(bundle.getString("recharge_success"));
            }
            else{
                Message.setText("Recharge successfully!");
            }
        }else if("DEBIT_CARD".equalsIgnoreCase(paymentMethod)){
            if(is_french){
                Message.setText(bundle.getString("recharge_success"));
            }
            else{
                Message.setText("Recharge successfully!");
            }
        }else if("CASH".equalsIgnoreCase(paymentMethod)){
            if(is_french){
                Message.setText(bundle.getString("recharge_success"));
            }
            else{
                Message.setText("Recharge successfully!");
            }
        }else {
            Message.setText("Please scan QR code to pay.");
            QRCodeGenerator.getInstance().generateQRCode();
            Image image;
            image = new Image(getClass().getResourceAsStream("/QR/payment_code.png"),250,250,false,false);
            QRCode.setGraphic(new ImageView(image));
        }


    }
    private void updateEmailAddressVisibility() {
        RadioButton selectedInvoiceOption = (RadioButton) invoiceToggleGroup.getSelectedToggle();
        String userData = selectedInvoiceOption.getUserData().toString();
        emailAddressHBox.setVisible(userData.equals("EMAIL_INVOICE"));
    }


}
