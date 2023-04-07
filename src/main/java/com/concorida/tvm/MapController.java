package com.concorida.tvm;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MapController {
    @FXML
    private AnchorPane mapPane;
    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResourceAsStream("/image/metro.jpg"));

        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        mapPane.setBackground(new Background(backgroundImage));
    }

}
