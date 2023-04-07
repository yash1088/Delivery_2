package com.concorida.tvm;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @author Yash Radadiya
 * @created 06/04/2023
 */

public class AlertBox {
    public void showAlert(String err_msg,boolean is_french){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (is_french){
            alert.setTitle("Boîte de dialogue d'informations");
        }
        else {
            alert.setTitle("Information Dialog");
        }

        alert.setHeaderText(null);
        alert.setContentText(err_msg);

        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }
}
