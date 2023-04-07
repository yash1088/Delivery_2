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

public class PurchaseTicketController {
    String str="";
    boolean is_french = false;
    @FXML private Label labelPT0,labelPT1,labelPT2,labelPT3,labelPT4,labelPT5;
    @FXML private RadioButton rdPT1,rdPT2,rdPT3,rdPT4,rdPT5,rdPT6,rdPT7,rdPT8,rdPT9,rdPT10,rdPT11,rdPT12,rdPT13,rdPT14,rdPT15,rdPT16,rdPT17;
    ResourceBundle bundle = ResourceBundle.getBundle("messages_fr", Locale.FRANCE);
    AlertBox alertBox = new AlertBox();

    @FXML
    private ToggleGroup ticketTypeToggleGroup;

    @FXML
    private ToggleGroup faresToggleGroup;
    @FXML
    private ToggleGroup paymentMethodToggleGroup;

    @FXML
    private Button payButton;

    @FXML
    private Label Message;

    @FXML
    private Label QRCode;
    @FXML
    private ToggleGroup invoiceToggleGroup;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private HBox emailAddressHBox;
    @FXML
    private Label Invoice_Msg;

    public void setMsg(boolean is_french){
        this.is_french = is_french;
        if(is_french){
            labelPT0.setText(bundle.getString("labelPT0"));
            labelPT1.setText(bundle.getString("labelPT1"));
            labelPT2.setText(bundle.getString("labelPT2"));
            labelPT3.setText(bundle.getString("labelPT3"));
            labelPT4.setText(bundle.getString("labelPT4"));
            labelPT5.setText(bundle.getString("labelPT5"));

            rdPT1.setText(bundle.getString("rdPT1"));
            rdPT2.setText(bundle.getString("rdPT2"));
            rdPT3.setText(bundle.getString("rdPT3"));
            rdPT4.setText(bundle.getString("rdPT4"));
            rdPT5.setText(bundle.getString("rdPT5"));
            rdPT6.setText(bundle.getString("rdPT6"));
            rdPT7.setText(bundle.getString("rdPT7"));
            rdPT8.setText(bundle.getString("rdPT8"));
            rdPT9.setText(bundle.getString("rdPT9"));
            rdPT10.setText(bundle.getString("rdPT10"));
            rdPT11.setText(bundle.getString("rdPT11"));
            rdPT12.setText(bundle.getString("rdPT12"));
            rdPT13.setText(bundle.getString("rdPT13"));
            rdPT14.setText(bundle.getString("rdPT14"));
            rdPT15.setText(bundle.getString("rdPT15"));
            rdPT16.setText(bundle.getString("rdPT16"));
            rdPT17.setText(bundle.getString("rdPT17"));

            payButton.setText(bundle.getString("button_PT"));
        }
    }

    @FXML
    private void initialize() {
        updateEmailAddressVisibility();

        invoiceToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                updateEmailAddressVisibility();
            }
        });
        payButton.setOnAction(event -> {
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
                else {
                    handlePay();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        ticketTypeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            updateFaresToggleGroupText();
        });
        updateFaresToggleGroupText();
    }

    private void updateFaresToggleGroupText(){
        RadioButton selectedTicketType = (RadioButton) ticketTypeToggleGroup.getSelectedToggle();
        String ticketTypeText = selectedTicketType.getText();

        switch (ticketTypeText) {
            case "STM-1 trip 3.25$":
                setFaresToggleGroupText("1 Fare " + 3.25 + "$", "2 Fares " + 3.25*2 + "$",
                        "3 Fares " + 3.25*3 + "$", "4 Fares " + 3.25*4 + "$");
                break;
            case "STM-2 trips 6.00$":
                setFaresToggleGroupText("1 Fare " + 6.00 + "$", "2 Fares " + 6.00*2 + "$",
                        "3 Fares " + 6.00*3 + "$", "4 Fares " + 6.00*4 + "$");
                break;
            case "STM-3 trips 8.75$":
                setFaresToggleGroupText("1 Fare " + 8.75 + "$", "2 Fares " + 8.75*2 + "$",
                        "3 Fares " + 8.75*3 + "$", "4 Fares " + 8.75*4 + "$");
                break;
            case "STM-Unlim. Weekend 13.00$":
                setFaresToggleGroupText("1 Fare " + 13.00 + "$", "2 Fares " + 13.00*2 + "$",
                        "3 Fares " + 13.00*3 + "$", "4 Fares " + 13.00*4 + "$");
                break;
            case "STM-1 day 10.00$":
                setFaresToggleGroupText("1 Fare " + 10.00 + "$", "2 Fares " + 10.00*2 + "$",
                        "3 Fares " + 10.00*3 + "$", "4 Fares " + 10.00*4 + "$");
                break;
            case "STM-Unlim. evening 5.00$":
                setFaresToggleGroupText("1 Fare " + 5.00 + "$", "2 Fares " + 5.00*2 + "$",
                        "3 Fares " + 5.00*3 + "$", "4 Fares " + 5.00*4 + "$");
                break;
        }

    }

    private void setFaresToggleGroupText(String text1, String text2, String text3, String text4) {
        ((RadioButton) faresToggleGroup.getToggles().get(0)).setText(text1);
        ((RadioButton) faresToggleGroup.getToggles().get(1)).setText(text2);
        ((RadioButton) faresToggleGroup.getToggles().get(2)).setText(text3);
        ((RadioButton) faresToggleGroup.getToggles().get(3)).setText(text4);
    }

    @FXML
    private void handlePay() throws IOException, WriterException, URISyntaxException {

        Invoice_Msg.setText("");
        RadioButton selectedRechargeAmount = (RadioButton) faresToggleGroup.getSelectedToggle();
        String rechargeAmount = (String) selectedRechargeAmount.getUserData();

        RadioButton selectedPaymentMethod = (RadioButton) paymentMethodToggleGroup.getSelectedToggle();
        String paymentMethod = (String) selectedPaymentMethod.getUserData();

        payForTickets(rechargeAmount, paymentMethod);
    }

    private void payForTickets(String rechargeAmount, String paymentMethod) throws IOException, WriterException, URISyntaxException {
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
        } else {
            if(is_french){
                Message.setText(bundle.getString("QR"));
            }
            else{
                Message.setText("Please scan QR code to pay");
            }
            QRCodeGenerator.getInstance().generateQRCode();
            Image image;
            image = new Image(getClass().getResourceAsStream("/QR/payment_code.png"),250,250,false,false);
            QRCode.setGraphic(new ImageView(image));
        }
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
                System.out.println("iii");
                Invoice_Msg.setText(bundle.getString("invoice_msg_email"));
            }
            else{
                Invoice_Msg.setText("The invoice printed");
            }
        }
    }
    private void updateEmailAddressVisibility() {
        RadioButton selectedInvoiceOption = (RadioButton) invoiceToggleGroup.getSelectedToggle();
        String userData = selectedInvoiceOption.getUserData().toString();
        emailAddressHBox.setVisible(userData.equals("EMAIL_INVOICE"));
    }

}
